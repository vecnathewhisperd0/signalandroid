package org.thoughtcrime.securesms.export;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import org.signal.core.util.logging.Log;
import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.database.DatabaseFactory;
import org.thoughtcrime.securesms.database.NoExternalStorageException;
import org.thoughtcrime.securesms.permissions.Permissions;
import org.thoughtcrime.securesms.recipients.RecipientId;
import org.thoughtcrime.securesms.util.StorageUtil;
import org.thoughtcrime.securesms.util.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The ChatExportFragment include the management elements
 * for starting the conversation export process.
 *
 * @author  @anlaji
 * @version 2.2
 * @since   2021-09-02
 */


public class ChatExportFragment extends Fragment {

    private static final String TAG = ChatExportFragment.class.getSimpleName ();
    private static final String      RECIPIENT_ID      = "RECIPIENT_ID";
    private static final String      FROM_CONVERSATION = "FROM_CONVERSATION";
    private static       long        existingThread;
    private static ExportZipUtil zip;
    private View         allMedia;
    private View         htmlViewer;
    private SwitchCompat allMediaSwitch;
    private SwitchCompat htmlViewerSwitch;
    private View         selectTimePeriod;
    private TextView     selectedTimePeriod;
    private Button       exportButton;
    private        boolean       includeHTMLViewer;
    private        boolean       includeAllMedia;

    private static void performSaveToDisk (@NonNull Context context,
                                           @NonNull Collection<ChatFormatter.MediaRecord> mediaRecords,
                                           HashMap<String, Uri> moreFiles,
                                           boolean hasViewer,
                                           String resultXML) {

        new AsyncTask<Void, Void, List<ExportZipUtil.Attachment>> ()
        {

            final List<ExportZipUtil.Attachment> attachments = new LinkedList<> ();

            @Override
            protected void onPreExecute() {
                super.onPreExecute ();
            }

            @Override
            protected List<ExportZipUtil.Attachment> doInBackground(Void... params) {
                if (!Util.isEmpty (mediaRecords))
                    for (ChatFormatter.MediaRecord mediaRecord : mediaRecords) {
                        assert mediaRecord.getAttachment () != null;
                        if (mediaRecord.getAttachment ().getUri () != null) {
                            attachments.add (new ExportZipUtil.Attachment (mediaRecord.getAttachment ().getUri (),
                                    mediaRecord.getContentType (),
                                    mediaRecord.getDate (),
                                    mediaRecord.getAttachment ().getSize ()));
                        }
                        if (isCancelled ()) break;
                    }
                if (!Util.isEmpty (moreFiles.entrySet ()))
                    for (Map.Entry<String, Uri> e : moreFiles.entrySet ())
                        if (e.getValue () != null) try {
                            if (Build.VERSION.SDK_INT >= 26) {
                                attachments.add (new ExportZipUtil.Attachment (e.getValue (),
                                        Files.probeContentType (Paths.get (e.getValue ().getPath ())),
                                        new Date ().getTime (),
                                        (new File (String.valueOf (e.getValue ()))).length ()));
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace ();
                        }
                return attachments;
            }

            @Override
            protected void onPostExecute(List<ExportZipUtil.Attachment> attachments) {
                super.onPostExecute(attachments);
                try {
                    zip = new ExportZipUtil (context, attachments.size(), existingThread, moreFiles);
                    zip.startToExport (context, hasViewer, resultXML);
                } catch (IOException | NoExternalStorageException e) {
                    e.printStackTrace ();
                    Log.w(TAG, e);
                }
                try{
                    if (!Util.isEmpty(attachments))
                        zip.executeOnExecutor (THREAD_POOL_EXECUTOR, attachments.toArray (new ExportZipUtil.Attachment[0]));
                    else
                        zip.executeOnExecutor (THREAD_POOL_EXECUTOR, (ExportZipUtil.Attachment[])  null);
                }catch (IllegalStateException e) {
                    e.printStackTrace ();
                    Log.w(TAG, e);
                }

            }
            @Override
            protected void onCancelled() {
                super.onCancelled();
            }


        }.execute();
    }

    public ChatExportFragment newInstance (@NonNull RecipientId recipientId, boolean fromConversation) {
        ChatExportFragment fragment = new ChatExportFragment ();
        Bundle args = new Bundle ();
        args.putParcelable (RECIPIENT_ID, recipientId);
        args.putBoolean (FROM_CONVERSATION, fromConversation);
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.chat_export_fragment, container, false);
        assert getArguments () != null;
        existingThread = DatabaseFactory.getThreadDatabase (this.getContext ()).getThreadIdIfExistsFor (getArguments ().getParcelable (RECIPIENT_ID));

        this.allMediaSwitch = view.findViewById (R.id.include_all_media_switch);
        this.allMedia = view.findViewById (R.id.include_all_media);
        this.htmlViewerSwitch = view.findViewById (R.id.include_html_viewer_switch);
        this.htmlViewer = view.findViewById (R.id.include_html_viewer);
        this.selectTimePeriod = view.findViewById (R.id.chat_export_select_time_period);
        this.selectedTimePeriod = view.findViewById (R.id.chat_export_selected_time_period);
        this.exportButton = view.findViewById (R.id.chat_export_button);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ChatExportViewModel viewModel  = ViewModelProviders.of(requireActivity()).get(ChatExportViewModel.class);
        allMedia.setOnClickListener(v -> viewModel.toggleSelectAllMedia ());

        viewModel.getAllMedia().observe(getViewLifecycleOwner(), shouldIncludeAllMedia -> {
            if (shouldIncludeAllMedia != allMediaSwitch.isChecked()) {
                includeAllMedia = true;
                allMediaSwitch.setChecked(shouldIncludeAllMedia);
                includeAllMedia = false;
            }
        });

        allMediaSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!includeAllMedia) {
                viewModel.setAllMedia (isChecked);
            }
        });

        allMediaSwitch.setChecked(viewModel.getCurrentSelectionAllMedia ());

        htmlViewer.setOnClickListener(v -> viewModel.toggleSelectHTMLViewer ());
        viewModel.getViewer().observe(getViewLifecycleOwner(), shouldIncludeViewer -> {
            if (shouldIncludeViewer != htmlViewerSwitch.isChecked()) {
                includeHTMLViewer = true;
                htmlViewerSwitch.setChecked(shouldIncludeViewer);
                includeHTMLViewer = false;
            }
        });

        htmlViewerSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!includeHTMLViewer) {
                viewModel.setViewer (isChecked);
            }
        });

        htmlViewerSwitch.setChecked(viewModel.getCurrentSelectionViewer ());

        selectTimePeriod.setOnClickListener(unused -> Navigation.findNavController (view)
                .navigate (R.id.action_chatExportTimePicker));


        viewModel.getSelectedTimePeriod ().observe(getViewLifecycleOwner(), selectedTimePeriod::setText);


        exportButton.setOnClickListener (
                unused -> onCreateClicked (viewModel));
    }

    private void onCreateClicked (ChatExportViewModel viewModel)  {
    Toast.makeText (getContext (), "Start processing chat data", Toast.LENGTH_SHORT).show ();
        Date from = null, until = null;
        if(viewModel.getDateFrom().getValue()!=null)
            from = viewModel.getDateFrom ().getValue ().get ();
        if(viewModel.getDateUntil().getValue()!=null)
            until = viewModel.getDateUntil ().getValue ().get ();
        ChatFormatter formatter = new ChatFormatter (requireContext (),
                existingThread,
                from,
                until);

        Permissions.with(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .ifNecessary()
                .onAllGranted(() -> {
                    try {
                            createZip (formatter, viewModel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .withPermanentDenialDialog(getString(R.string.ChatExporter_signal_requires_external_storage_permission_in_order_to_create_chat_export_zip_file))
                .execute();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    void createZip (ChatFormatter exp, ChatExportViewModel viewModel) {
        String result = exp.parseConversationToXML ();
        if (result.length () > 0) {
            Map<String, ChatFormatter.MediaRecord> selectedMedia = new HashMap<> ();
            HashMap<String, Uri> otherFiles = new HashMap<> ();
            if (viewModel.getCurrentSelectionAllMedia ()) {
                selectedMedia = exp.getAllMedia ();
                for (Map.Entry<String, Uri> e : exp.getOtherFiles ().entrySet ()) {
                    otherFiles.put (e.getKey (), e.getValue ());
                }
            }
            handleSaveMedia (selectedMedia.values (), otherFiles, viewModel.getCurrentSelectionViewer (), result);
        } else
            Toast.makeText (getContext (), "No messages to export", Toast.LENGTH_SHORT).show ();
    }

    private void handleSaveMedia (
            @NonNull Collection<ChatFormatter.MediaRecord> mediaRecords, HashMap<String, Uri> moreFiles,
            boolean currentSelectionViewer,
            String result) {

        final Context context = requireContext ();

        if (StorageUtil.canWriteToMediaStore ()) {
            performSaveToDisk (context, mediaRecords, moreFiles, currentSelectionViewer, result);
            return;
        }

        ExportZipUtil.showWarningDialog (context, (dialogInterface, which) -> Permissions.with (this)
                        .request (Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .ifNecessary ()
                        .withPermanentDenialDialog (context.getString (R.string.ChatExport_signal_needs_the_storage_permission_in_order_to_write_to_external_storage_but_it_has_been_permanently_denied))
                        .onAnyDenied (() -> Toast.makeText (context, R.string.ChatExport_unable_to_write_to_external_storage_without_permission, Toast.LENGTH_LONG).show ())
                        .onAllGranted (() -> performSaveToDisk (context, mediaRecords, moreFiles, currentSelectionViewer, result))
                        .execute (),
                (mediaRecords.size () + moreFiles.size ()));
    }
}