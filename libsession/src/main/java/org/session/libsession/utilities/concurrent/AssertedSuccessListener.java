package org.session.libsession.utilities.concurrent;

import org.session.libsession.utilities.concurrent.ListenableFuture.Listener;

import java.util.concurrent.ExecutionException;

public abstract class AssertedSuccessListener<T> implements Listener<T> {
  @Override
  public void onFailure(ExecutionException e) {
    throw new AssertionError(e);
  }
}
