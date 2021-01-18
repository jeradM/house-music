import React, { useEffect, useRef } from 'react';
import Hls from 'hls.js'
import makeStyles from "@material-ui/core/styles/makeStyles";
import { useSelector } from "react-redux";
import classNames from 'classnames';

export default props => {
  const hassConn = useSelector(state => state.homeassistant.connection, []);
  const hassUrl = useSelector(state => state.config.hassUrl, []);
  const authData = hassConn && hassConn.options.auth.data;
  const classes = useStyles();
  const vidRef = useRef();
  const entityId = props.entityId;

  useEffect(() => {
    if (!vidRef.current || !hassConn) return;
    let _hls;
    hassConn.sendMessagePromise({
      type: 'camera/stream',
      entity_id: entityId
    })
      .then(res => {
        if (Hls.isSupported()) {
          _hls = new Hls();
          _hls.loadSource(`${hassUrl}${res.url}`);
          _hls.attachMedia(vidRef.current);
        } else {
          vidRef.current.src = res.url;
        }
      });
    return () => {
      _hls && _hls.destroy();
      vidRef && (vidRef.current.src = null);
    }
  }, [entityId, authData]);

  return (
    <div className={classes.container} onClick={() => props.select(entityId)}>
      <video className={classNames(classes.video, props.className)} ref={vidRef} autoPlay={true} />
    </div>
  )
}

const useStyles = makeStyles(theme => {
  return {
    container: {
      width: '100%',
      position: 'relative'
    },
    video: {
      width: '100%',
      boxShadow: theme.shadows[2],
      marginBottom: -4
    }
  }
});
