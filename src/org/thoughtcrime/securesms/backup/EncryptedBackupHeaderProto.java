// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: EncryptedBackupHeader.proto

package org.thoughtcrime.securesms.backup;

public final class EncryptedBackupHeaderProto {
  private EncryptedBackupHeaderProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface EncryptedBackupHeaderOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required bytes encryptionSalt = 1;
    /**
     * <code>required bytes encryptionSalt = 1;</code>
     */
    boolean hasEncryptionSalt();
    /**
     * <code>required bytes encryptionSalt = 1;</code>
     */
    com.google.protobuf.ByteString getEncryptionSalt();

    // required bytes macSalt = 2;
    /**
     * <code>required bytes macSalt = 2;</code>
     */
    boolean hasMacSalt();
    /**
     * <code>required bytes macSalt = 2;</code>
     */
    com.google.protobuf.ByteString getMacSalt();

    // required uint32 kdfIterations = 3;
    /**
     * <code>required uint32 kdfIterations = 3;</code>
     */
    boolean hasKdfIterations();
    /**
     * <code>required uint32 kdfIterations = 3;</code>
     */
    int getKdfIterations();
  }
  /**
   * Protobuf type {@code textsecure.EncryptedBackupHeader}
   */
  public static final class EncryptedBackupHeader extends
      com.google.protobuf.GeneratedMessage
      implements EncryptedBackupHeaderOrBuilder {
    // Use EncryptedBackupHeader.newBuilder() to construct.
    private EncryptedBackupHeader(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private EncryptedBackupHeader(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final EncryptedBackupHeader defaultInstance;
    public static EncryptedBackupHeader getDefaultInstance() {
      return defaultInstance;
    }

    public EncryptedBackupHeader getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private EncryptedBackupHeader(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              encryptionSalt_ = input.readBytes();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              macSalt_ = input.readBytes();
              break;
            }
            case 24: {
              bitField0_ |= 0x00000004;
              kdfIterations_ = input.readUInt32();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.internal_static_textsecure_EncryptedBackupHeader_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.internal_static_textsecure_EncryptedBackupHeader_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader.class, org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader.Builder.class);
    }

    public static com.google.protobuf.Parser<EncryptedBackupHeader> PARSER =
        new com.google.protobuf.AbstractParser<EncryptedBackupHeader>() {
      public EncryptedBackupHeader parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new EncryptedBackupHeader(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<EncryptedBackupHeader> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required bytes encryptionSalt = 1;
    public static final int ENCRYPTIONSALT_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString encryptionSalt_;
    /**
     * <code>required bytes encryptionSalt = 1;</code>
     */
    public boolean hasEncryptionSalt() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required bytes encryptionSalt = 1;</code>
     */
    public com.google.protobuf.ByteString getEncryptionSalt() {
      return encryptionSalt_;
    }

    // required bytes macSalt = 2;
    public static final int MACSALT_FIELD_NUMBER = 2;
    private com.google.protobuf.ByteString macSalt_;
    /**
     * <code>required bytes macSalt = 2;</code>
     */
    public boolean hasMacSalt() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required bytes macSalt = 2;</code>
     */
    public com.google.protobuf.ByteString getMacSalt() {
      return macSalt_;
    }

    // required uint32 kdfIterations = 3;
    public static final int KDFITERATIONS_FIELD_NUMBER = 3;
    private int kdfIterations_;
    /**
     * <code>required uint32 kdfIterations = 3;</code>
     */
    public boolean hasKdfIterations() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required uint32 kdfIterations = 3;</code>
     */
    public int getKdfIterations() {
      return kdfIterations_;
    }

    private void initFields() {
      encryptionSalt_ = com.google.protobuf.ByteString.EMPTY;
      macSalt_ = com.google.protobuf.ByteString.EMPTY;
      kdfIterations_ = 0;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasEncryptionSalt()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasMacSalt()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasKdfIterations()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, encryptionSalt_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, macSalt_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeUInt32(3, kdfIterations_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, encryptionSalt_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, macSalt_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(3, kdfIterations_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code textsecure.EncryptedBackupHeader}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeaderOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.internal_static_textsecure_EncryptedBackupHeader_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.internal_static_textsecure_EncryptedBackupHeader_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader.class, org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader.Builder.class);
      }

      // Construct using org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        encryptionSalt_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        macSalt_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000002);
        kdfIterations_ = 0;
        bitField0_ = (bitField0_ & ~0x00000004);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.internal_static_textsecure_EncryptedBackupHeader_descriptor;
      }

      public org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader getDefaultInstanceForType() {
        return org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader.getDefaultInstance();
      }

      public org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader build() {
        org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader buildPartial() {
        org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader result = new org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.encryptionSalt_ = encryptionSalt_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.macSalt_ = macSalt_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.kdfIterations_ = kdfIterations_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader) {
          return mergeFrom((org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader other) {
        if (other == org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader.getDefaultInstance()) return this;
        if (other.hasEncryptionSalt()) {
          setEncryptionSalt(other.getEncryptionSalt());
        }
        if (other.hasMacSalt()) {
          setMacSalt(other.getMacSalt());
        }
        if (other.hasKdfIterations()) {
          setKdfIterations(other.getKdfIterations());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasEncryptionSalt()) {
          
          return false;
        }
        if (!hasMacSalt()) {
          
          return false;
        }
        if (!hasKdfIterations()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (org.thoughtcrime.securesms.backup.EncryptedBackupHeaderProto.EncryptedBackupHeader) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required bytes encryptionSalt = 1;
      private com.google.protobuf.ByteString encryptionSalt_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes encryptionSalt = 1;</code>
       */
      public boolean hasEncryptionSalt() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required bytes encryptionSalt = 1;</code>
       */
      public com.google.protobuf.ByteString getEncryptionSalt() {
        return encryptionSalt_;
      }
      /**
       * <code>required bytes encryptionSalt = 1;</code>
       */
      public Builder setEncryptionSalt(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        encryptionSalt_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes encryptionSalt = 1;</code>
       */
      public Builder clearEncryptionSalt() {
        bitField0_ = (bitField0_ & ~0x00000001);
        encryptionSalt_ = getDefaultInstance().getEncryptionSalt();
        onChanged();
        return this;
      }

      // required bytes macSalt = 2;
      private com.google.protobuf.ByteString macSalt_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes macSalt = 2;</code>
       */
      public boolean hasMacSalt() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required bytes macSalt = 2;</code>
       */
      public com.google.protobuf.ByteString getMacSalt() {
        return macSalt_;
      }
      /**
       * <code>required bytes macSalt = 2;</code>
       */
      public Builder setMacSalt(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        macSalt_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes macSalt = 2;</code>
       */
      public Builder clearMacSalt() {
        bitField0_ = (bitField0_ & ~0x00000002);
        macSalt_ = getDefaultInstance().getMacSalt();
        onChanged();
        return this;
      }

      // required uint32 kdfIterations = 3;
      private int kdfIterations_ ;
      /**
       * <code>required uint32 kdfIterations = 3;</code>
       */
      public boolean hasKdfIterations() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>required uint32 kdfIterations = 3;</code>
       */
      public int getKdfIterations() {
        return kdfIterations_;
      }
      /**
       * <code>required uint32 kdfIterations = 3;</code>
       */
      public Builder setKdfIterations(int value) {
        bitField0_ |= 0x00000004;
        kdfIterations_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required uint32 kdfIterations = 3;</code>
       */
      public Builder clearKdfIterations() {
        bitField0_ = (bitField0_ & ~0x00000004);
        kdfIterations_ = 0;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:textsecure.EncryptedBackupHeader)
    }

    static {
      defaultInstance = new EncryptedBackupHeader(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:textsecure.EncryptedBackupHeader)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_textsecure_EncryptedBackupHeader_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_textsecure_EncryptedBackupHeader_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\033EncryptedBackupHeader.proto\022\ntextsecur" +
      "e\"W\n\025EncryptedBackupHeader\022\026\n\016encryption" +
      "Salt\030\001 \002(\014\022\017\n\007macSalt\030\002 \002(\014\022\025\n\rkdfIterat" +
      "ions\030\003 \002(\rB?\n!org.thoughtcrime.securesms" +
      ".backupB\032EncryptedBackupHeaderProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_textsecure_EncryptedBackupHeader_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_textsecure_EncryptedBackupHeader_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_textsecure_EncryptedBackupHeader_descriptor,
              new java.lang.String[] { "EncryptionSalt", "MacSalt", "KdfIterations", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
