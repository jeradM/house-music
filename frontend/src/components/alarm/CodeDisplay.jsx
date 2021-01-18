import React from 'react';
import classNames from 'classnames';
import { makeStyles } from "@material-ui/styles";
import { Asterisk, BackspaceOutline } from 'mdi-material-ui';
import IconButton from "@material-ui/core/IconButton";

export default props => {
  if (!props.alarm) return null;
  const classes = useStyles();
  const items = [...Array(props.codeLength).keys()];

  const stateObj = props.alarm;
  const state = stateObj.state;
  const nextState = stateObj.attributes.post_pending_state;
  const [pending, triggered] = ['pending', 'triggered'].map(s => s === state);
  const pendingTrigger = pending && nextState === 'triggered';

  return (
    <div
      className={classNames(
        classes.displayContainer,
        props.className,
        {[classes.triggered]: triggered},
        {[classes.pendingTrigger]: pendingTrigger}
      )}
    >
      <div className={classes.codeContainer}>
        {items.map((_, i) => (
          <Asterisk key={i} classes={{root: classes.code}} />
        ))}
      </div>
      { items.length ?
        <div className={classes.backspaceContainer}>
          <IconButton onClick={props.onClear} className={classes.backspace}>
            <BackspaceOutline fontSize="large" />
          </IconButton>
        </div> :
        null
      }
    </div>
  )
}

const useStyles = makeStyles(theme => {
  return {
    displayContainer: {
      display: 'flex',
      alignItems: 'center',
      borderBottom: `1px solid ${theme.palette.grey[300]}`,
      background: theme.palette.grey[100],
      borderRadius: 8
    },
    triggered: {
      border: `4px solid ${theme.alarm.red}`
    },
    pendingTrigger: {

      border: `4px solid ${theme.alarm.yellow}`
    },
    codeContainer: {
      flex: '1 1 auto',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      paddingLeft: 80
    },
    code: {
      fontSize: '3rem',
      padding: '0 4px',
      color: theme.palette.grey[600]
    },
    backspaceContainer: {
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
    },
    backspace: {
      padding: 28
    }
  }
});
