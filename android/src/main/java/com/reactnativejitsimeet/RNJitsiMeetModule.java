package com.reactnativejitsimeet;

import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.net.URL;
import java.net.MalformedURLException;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ReadableMap;

import org.jitsi.meet.sdk.BroadcastIntentHelper;

@ReactModule(name = RNJitsiMeetModule.MODULE_NAME)
public class RNJitsiMeetModule extends ReactContextBaseJavaModule {
    public static final String MODULE_NAME = "RNJitsiMeetModule";
    private IRNJitsiMeetViewReference mJitsiMeetViewReference;

    public RNJitsiMeetModule(ReactApplicationContext reactContext, IRNJitsiMeetViewReference jitsiMeetViewReference) {
        super(reactContext);
        mJitsiMeetViewReference = jitsiMeetViewReference;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void initialize() {
        Log.d("JitsiMeet", "Initialize is deprecated in v2");
    }

    @ReactMethod
    public void call(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNJitsiMeetConferenceOptions options = new RNJitsiMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(false)
                            .setUserInfo(_userInfo)
                            .build();
                    mJitsiMeetViewReference.getJitsiMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void audioCall(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNJitsiMeetConferenceOptions options = new RNJitsiMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(true)
                            .setUserInfo(_userInfo)
                            .build();
                    mJitsiMeetViewReference.getJitsiMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void endCall() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    mJitsiMeetViewReference.getJitsiMeetView().leave();
                }
            }
        });
    }

    @ReactMethod
    public void openChat(String to) {
        try {
            Log.d("JitsiMeet", "openChat: Performing open chat");
            Intent chatBroadcastIntent = BroadcastIntentHelper.buildOpenChatIntent(to);
            LocalBroadcastManager.getInstance(getReactApplicationContext()).sendBroadcast(chatBroadcastIntent);
        }
        catch(Exception e) {
            Log.d("JitsiMeet", "openChat: Error in open chat");
        }
    }


    @ReactMethod
    public void closeChat() {
        try {
            Log.d("JitsiMeet", "closeChat: Performing close chat");
            Intent chatBroadcastIntent = BroadcastIntentHelper.buildCloseChatIntent();
            LocalBroadcastManager.getInstance(getReactApplicationContext()).sendBroadcast(chatBroadcastIntent);
        }
        catch(Exception e) {
            Log.d("JitsiMeet", "closeChat: Error in close chat");
        }
    }

    @ReactMethod
    public void setAudioMuted(Boolean muted) {
        try {
            Log.d("JitsiMeet", "setAudioMuted: Performing setAudioMuted");
            Intent audioBroadcastIntent = BroadcastIntentHelper.buildSetAudioMutedIntent(muted);
            LocalBroadcastManager.getInstance(getReactApplicationContext()).sendBroadcast(audioBroadcastIntent);
        }
        catch(Exception e) {
            Log.d("JitsiMeet", "setAudioMuted: Error in setAudioMuted");
        }
    }

    @ReactMethod
    public void setVideoMuted(Boolean muted) {
        try {
            Log.d("JitsiMeet", "setVideoMuted: Performing setVideoMuted");
            Intent videoBroadcastIntent = BroadcastIntentHelper.buildSetVideoMutedIntent(muted);
            LocalBroadcastManager.getInstance(getReactApplicationContext()).sendBroadcast(videoBroadcastIntent);
        }
        catch(Exception e) {
            Log.d("JitsiMeet", "setVideoMuted: Error in setVideoMuted");
        }
    }
}
