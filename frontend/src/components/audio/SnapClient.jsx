import React from 'react';
import { useDispatch, useSelector } from 'react-redux';

import Typography from '@material-ui/core/Typography/Typography';
import CardContent from '@material-ui/core/CardContent/index';
import CardMedia from '@material-ui/core/CardMedia/index';
import Card from '@material-ui/core/Card/index';
import IconButton from '@material-ui/core/IconButton/index';
import CardHeader from '@material-ui/core/CardHeader/index';
import Avatar from '@material-ui/core/Avatar/index';
import SkipPrevious from '@material-ui/icons/SkipPrevious';
import SkipNext from '@material-ui/icons/SkipNext';
import PlayArrow from '@material-ui/icons/PlayArrow';
import Pause from '@material-ui/icons/Pause';
import VolumeDown from '@material-ui/icons/VolumeDown';
import VolumeUp from '@material-ui/icons/VolumeUp';
import VolumeOff from '@material-ui/icons/VolumeOff';
import LinearProgress from '@material-ui/core/LinearProgress/index';

import { openModal } from '../../actions/modal';
import { makeStyles } from '@material-ui/styles/index';

import { send } from '../../commands';

export default props => {
  const classes = useStyles();
  const dispatch = useDispatch();

  const streams = useSelector(state => state.streams, null);
  const stream = streams.find(s => s.id === props.client.stream);
  if (!stream) return null;

  const { id, muted, volume } = props.client;
  const playing = stream.status === 'PLAYING';
  const { track } = stream;

  const sendStreamCommand = type => {
    send(`stream/${stream.id}/${type}`, 'put');
  };

  const sendClientCommand = type => {
    send(`client/${id}/${type}`, 'put');
  };

  return (
    <div className={classes.clientContainer}>
      <Card className={classes.card}>
        <CardHeader
          className={classes.header}
          avatar={
            <Avatar className={classes.avatar}>
              {getName(id).substring(0, 1)}
            </Avatar>
          }
          title={getName(id)}
        />
        <CardMedia
          className={classes.albumArt}
          id={id}
          image={track && track.album && track.album.imageUri}
          onClick={() => dispatch(openModal(id))}
          title="album-art"
        />
        <CardContent className={classes.cardContent}>
          <div>
            <Typography
              align="center"
              gutterBottom
              variant="h5"
              noWrap={true}
              component="h5"
              className={classes.track}
            >
              {track ? track.title : 'No Track Playing'}
            </Typography>
            <Typography
              align="center"
              gutterBottom
              variant="h6"
              noWrap={true}
              color="textSecondary"
              className={classes.artist}
            >
              {track && track.album && track.album.artist}
            </Typography>
          </div>
          <div className={classes.controls}>
            <IconButton onClick={() => sendStreamCommand('prev')}>
              <SkipPrevious className={classes.playbackSm} />
            </IconButton>
            <IconButton
              onClick={() => sendStreamCommand(playing ? 'pause' : 'resume')}
            >
              {playing ? (
                <Pause className={classes.playbackLg} />
              ) : (
                <PlayArrow className={classes.playbackLg} />
              )}
            </IconButton>
            <IconButton onClick={() => sendStreamCommand('next')}>
              <SkipNext className={classes.playbackSm} />
            </IconButton>
          </div>
          <div className={classes.controls}>
            <IconButton onClick={() => sendClientCommand('volDown')}>
              <VolumeDown className={classes.volumeSm} />
            </IconButton>
            <IconButton
              onClick={() => sendClientCommand(muted ? 'unmute' : 'mute')}
              className={muted ? classes.muted : ''}
            >
              <VolumeOff className={classes.volumeLg} />
            </IconButton>
            <IconButton onClick={() => sendClientCommand('volUp')}>
              <VolumeUp className={classes.volumeSm} />
            </IconButton>
          </div>
          <LinearProgress
            color="primary"
            value={volume}
            variant="determinate"
            className={classes.volumeProgress}
          />
        </CardContent>
      </Card>
    </div>
  );
};

const getName = id => {
  return id
    .toString()
    .toLowerCase()
    .split('-')
    .map(s => s.charAt(0).toUpperCase() + s.substring(1))
    .join(' ');
};

const useStyles = makeStyles(theme => {
  return {
    clientContainer: {
      height: '100%',
    },

    card: {
      height: '100%',
      display: 'grid',
      margin: 4,
      gridTemplateRows: '40px 1fr 2fr',
    },

    avatar: {
      background: theme.palette.primary.main,
      fontSize: '80%',
      width: 20,
      height: 20,
    },

    cardContent: {
      flex: 1,
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'space-around',
      position: 'relative',
    },

    controls: {
      display: 'flex',
      alignItems: 'center',
      width: '100%',
      justifyContent: 'space-around',
    },

    track: {
      fontSize: '150%',
    },

    artist: {
      fontSize: '120%',
    },

    muted: {
      transition: 'all .4s linear',
      background: 'rgba(0, 0, 0, .1)',
    },

    playbackSm: {
      fontSize: '140%',
    },

    playbackLg: {
      fontSize: '200%',
    },

    volumeSm: {
      fontSize: '120%',
    },

    volumeLg: {
      fontSize: '170%',
    },

    actionButton: {
      position: 'absolute',
      bottom: 20,
      right: 20,
    },

    volumeProgress: {
      position: 'absolute',
      bottom: '0',
      left: '0',
      width: '100%',
    },

    '@media (max-width: 600px)': {
      card: {
        gridTemplateRows: '50px 250px',
        gridTemplateColumns: '1fr 2fr',
      },
      header: {
        gridColumnStart: '1',
        gridColumnEnd: '3',
        padding: 8,
      },
      info: {
        fontSize: '.85rem',
      },
      playbackSm: {
        fontSize: 36,
      },

      playbackLg: {
        fontSize: 60,
      },

      volumeSm: {
        fontSize: 27,
      },

      volumeLg: {
        fontSize: 45,
      },
    },
  };
});
