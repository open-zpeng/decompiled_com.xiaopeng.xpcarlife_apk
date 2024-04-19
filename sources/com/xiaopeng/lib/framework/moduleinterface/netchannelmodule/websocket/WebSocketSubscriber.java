package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okio.ByteString;
/* loaded from: classes.dex */
public abstract class WebSocketSubscriber implements Observer<IWebSocketInfo> {
    private Disposable disposable;
    private boolean hasOpened;

    protected void onClose() {
    }

    protected void onClosed(int code, String reason) {
    }

    protected void onMessage(String text) {
    }

    protected void onMessage(ByteString byteString) {
    }

    protected void onOpen() {
    }

    protected void onReconnect() {
    }

    @Override // io.reactivex.Observer
    public final void onNext(IWebSocketInfo webSocketInfo) {
        if (webSocketInfo.isOnOpen()) {
            this.hasOpened = true;
            onOpen();
        } else if (webSocketInfo.isClosed()) {
            this.hasOpened = false;
            onClosed(webSocketInfo.closedReasonCode(), webSocketInfo.closedReason());
        } else if (webSocketInfo.stringMessage() != null) {
            onMessage(webSocketInfo.stringMessage());
        } else if (webSocketInfo.byteStringMessage() != null) {
            onMessage(webSocketInfo.byteStringMessage());
        } else if (webSocketInfo.isOnReconnect()) {
            onReconnect();
        }
    }

    @Override // io.reactivex.Observer
    public final void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    public final void dispose() {
        Disposable disposable = this.disposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.Observer
    public final void onComplete() {
        if (this.hasOpened) {
            onClose();
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
