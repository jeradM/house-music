import React, { useState } from 'react';
import Keypad from './Keypad';
import { makeStyles } from "@material-ui/styles";
import CodeDisplay from "./CodeDisplay";
import { useSelector } from "react-redux";
import { ALARM_ENTITY as entity_id } from "../../reducers/homeassistant-reducer";
import AlarmActions from "./AlarmActions";
import { callService } from "home-assistant-js-websocket";

const DOMAIN = entity_id.split('.')[0];

let setCode;
const callAlarmService = (conn, code, service) => {
  callService(conn, DOMAIN, service, {entity_id, code})
  setCode('');
}

export default function(props) {
  const [ code, _setCode ] = useState('');
  setCode = _setCode;
  const hassConn = useSelector(state => state.homeassistant.connection, []);
  const alarmStateObj = useSelector(state => state.homeassistant.entities[entity_id], []);

  const onKey = key => setCode(code.length < 8 ? `${code}${key}` : code);
  const onClear = () => setCode(code.slice(0, -1));

  const disarm = () => callAlarmService(hassConn, code, 'alarm_disarm');
  const armHome = () => callAlarmService(hassConn, code, 'alarm_arm_home');
  const armAway = () => callAlarmService(hassConn, code, 'alarm_arm_away');

  const classes = useStyles();
  return (
    <div className={classes.alarmContainer}>
      <CodeDisplay
        codeLength={code.length}
        className={classes.display}
        onClear={onClear}
        alarm={alarmStateObj}
      />
      <Keypad
        className={classes.keypad}
        onKey={onKey}
      />
      <AlarmActions
        className={classes.actions}
        disarm={disarm}
        armHome={armHome}
        armAway={armAway}
        alarm={alarmStateObj} />
    </div>
  )
}

const useStyles = makeStyles(theme => {
  return {
    alarmContainer: {
      height: '100%',
      display: 'grid',
      gridTemplateColumns: '3fr 2fr',
      gridTemplateRows: '1fr 2fr',
      gridGap: theme.grid.spacing * 8
    },
    display: {
      gridColumn: '1 / span 2',
      gridRow: 1
    },
    keypad: {
      gridColumn: 1,
      gridRow: 2
    },
    actions: {
      gridColumn: 2,
      gridRow: 2
    }
  }
});
