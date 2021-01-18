package com.jeradmeisner.audioserver.mopidy.notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jeradmeisner.audioserver.mopidy.dao.TracklistTrack;
import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyNotificationHandler;

@MopidyNotificationHandler(type = {
        "track_playback_ended",
        "track_playback_paused",
        "track_playback_resumed",
        "track_playback_started"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackPlaybackStateNotification extends MopidyNotification {
    public TracklistTrack tlTrack;

    public TrackPlaybackStateNotification() {
        super(NotificationType.TRACK_PLAYBACK_STATE);
    }

    @Override
    public void load() {
        this.tlTrack = mapper.convertValue(additionalProperties.get("tl_track"), TracklistTrack.class);
    }
}
