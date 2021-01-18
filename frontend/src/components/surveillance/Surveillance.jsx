import React, { useState } from 'react';
import { CAMERA_ENTITIES } from "../../reducers/homeassistant-reducer";
import { makeStyles } from "@material-ui/core";
import Camera from "./Camera";

export default props => {
  const [selected, setSelected] = useState(null);
  const classes = useStyles();
  console.log(selected);
  return (
    <div className={selected ? classes.single : classes.grid}>
      { selected ?
        <Camera entityId={selected} select={() => setSelected(null)} className={classes.singleCam} /> :
        CAMERA_ENTITIES.map((e, idx) => (
          <Camera select={setSelected} entityId={e} key={idx} />
        ))
      }
    </div>
  )
}

const useStyles = makeStyles(theme => {
  return {
    grid: {
      display: 'grid',
      gridTemplateColumns: 'repeat(auto-fill, minmax(45%, 1fr))',
      gridAutoRows: '1fr',
      gridGap: 8,
      height: '100%',
      overflowY: 'auto'
    },
    single: {
      height: '100%',
      position: 'relative',
      display: 'flex',
      justifyContent: 'stretch',
      alignItems: 'center'
    },
    singleCam: {
      boxShadow: theme.shadows[4]
    }
  }
});
