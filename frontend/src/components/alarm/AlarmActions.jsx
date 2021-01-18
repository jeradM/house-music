import React from 'react';
import Button from "@material-ui/core/Button";
import classNames from 'classnames';
import { makeStyles } from "@material-ui/styles";

export default props => {
  if (!props.alarm) return null;
  const classes = useStyles();
  const stateObj = props.alarm;
  const state = stateObj.state;
  const nextState = stateObj.attributes.post_pending_state;
  const [pending, disarmed, armedHome, armedAway] =
    ['pending', 'disarmed', 'armed_home', 'armed_away'].map(s => s === state);
  const pendingTrigger = pending && nextState === 'triggered';
  const pendingAway = pending && nextState === 'armed_away';
  return (
    <div className={classNames(classes.container, props.className)}>
      <Button
        className={classes.action}
        color="primary"
        onClick={props.disarm}
        variant={pendingTrigger ? 'outlined' : (disarmed ? 'contained' : 'text')}
      >{disarmed ? 'Disarmed' : 'Disarm'}</Button>
      <Button
        className={classes.action}
        color="primary"
        onClick={props.armHome}
        variant={pendingTrigger ? 'outlined' : (armedHome ? 'contained' : 'text')}
      >{armedHome ? 'Armed Home' : 'Arm Home'}</Button>
      <Button
        className={
          classNames(
            classes.action,
            {[classes.pendingAway]: pendingAway}
          )
        }
        color="primary"
        onClick={props.armAway}
        variant={pendingTrigger || pendingAway ? 'outlined' : (armedAway ? 'contained' : 'text')}
      >{armedAway ? 'Armed Away' : 'Arm Away'}</Button>
    </div>
  )
}

const useStyles = makeStyles(theme => {
  return {
    container: {
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'stretch',
      alignItems: 'stretch',
      padding: 16
    },
    action: {
      padding: '16px 0',
      fontSize: '2rem',
      flex: 1,
      margin: '8px 0'
    },
    '@keyframes pulseBorder': {
      '0%': {borderColor: theme.alarm.yellow, color: theme.alarm.yellow},
      '50%': {borderColor: theme.palette.primary.main, color: theme.palette.primary.main},
      '100%': {borderColor: theme.alarm.yellow, color: theme.alarm.yellow}
    },
    pendingAway: {
      animation: '$pulseBorder 3s infinite',
      border: `2px solid ${theme.alarm.yellow}`,
      color: theme.alarm.yellow
    }
  }
});
