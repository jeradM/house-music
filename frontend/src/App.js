import React from 'react';
import Dialog from '@material-ui/core/Dialog';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import { init } from './actions/init';
import { closeModal } from './actions/modal';
import StreamModal from './components/audio/StreamModal';
import Slide from '@material-ui/core/Slide';
import Drawer from '@material-ui/core/Drawer';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import { Cctv, Music as MusicIcon, Refresh, ShieldHome } from 'mdi-material-ui';
import {
  BrowserRouter as Router,
  NavLink,
  Redirect,
  Route,
} from 'react-router-dom';
import Music from './components/audio/Music';
import Alarm from './components/alarm/Alarm';
import Surveillance from './components/surveillance/Surveillance';

class SlideUp extends React.Component {
  render() {
    return <Slide direction="up" {...this.props} />;
  }
}

// class NavigationLink extends React.Component {
//   render() {
//     return (<NavLink {...this.props} />)
//   }
// }

const NavigationLink = React.forwardRef((props, ref) => (
  <NavLink innerRef={ref} {...props} />
));

class App extends React.Component {
  componentWillMount() {
    this.props.init();
  }

  render() {
    const { classes } = this.props;
    return (
      <Router>
        <div className={classes.App}>
          <Drawer
            classes={{ paper: classes.drawer }}
            variant="permanent"
            open={false}
          >
            <List component="nav" className={classes.nav}>
              <ListItem
                button
                classes={{ button: classes.listItem }}
                component={NavigationLink}
                to="/music"
              >
                <ListItemIcon>
                  <MusicIcon className={classes.drawerIcon} color="inherit" />
                </ListItemIcon>
              </ListItem>
              <ListItem
                button
                classes={{ button: classes.listItem }}
                component={NavigationLink}
                to="/alarm"
              >
                <ListItemIcon>
                  <ShieldHome className={classes.drawerIcon} />
                </ListItemIcon>
              </ListItem>
              <ListItem
                button
                classes={{ button: classes.listItem }}
                component={NavigationLink}
                to="/cams"
              >
                <ListItemIcon>
                  <Cctv className={classes.drawerIcon} />
                </ListItemIcon>
              </ListItem>
              <ListItem
                button
                component="div"
                classes={{ button: classes.listItem }}
                onClick={() => window.location.reload()}
              >
                <ListItemIcon>
                  <Refresh className={classes.drawerIcon} />
                </ListItemIcon>
              </ListItem>
            </List>
          </Drawer>
          <div className={classes.mainContent}>
            <Route exact path="/" component={() => <Redirect to="/music" />} />
            <Route path="/music" component={Music} />
            <Route path="/alarm" component={Alarm} />
            <Route path="/cams" component={Surveillance} />
          </div>
          <Dialog
            open={this.props.modalOpen}
            onClose={this.props.closeModal}
            fullWidth
            fullScreen
            maxWidth="xl"
            TransitionComponent={SlideUp}
          >
            <StreamModal />
          </Dialog>
        </div>
      </Router>
    );
  }

  onStreamSelected(streamId) {
    this.closeModal();
    console.log(`Stream: ${streamId}`);
  }

  onPlaylistSelected(playlistId) {
    this.closeModal();
    console.log(`Playlist: ${playlistId}`);
  }
}

const mapStateToProps = state => {
  return {
    clients: state.clients,
    streams: state.streams,
    modalOpen: state.modal.open,
  };
};

const styles = theme => ({
  App: {
    height: '100%',
    position: 'relative',
    display: 'flex',
  },
  drawer: {
    width: 72,
    background: theme.palette.primary.main,
    position: 'relative',
  },
  nav: {
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
  },
  drawerIcon: {
    width: 40,
    height: 40,
    color: 'white',
  },
  listItem: {
    padding: 16,
    '&:last-child': {
      marginTop: 'auto',
    },
  },
  mainContent: {
    flex: '1 1 auto',
    padding: 2,
  },
});

export default connect(
  mapStateToProps,
  { init, closeModal }
)(withStyles(styles, { withTheme: true })(App));
