import React from 'react';
import { useSelector } from "react-redux";
import { makeStyles } from "@material-ui/styles";
import { getEntityComponent } from "./helpers";
import ClockEntity from "./entities/ClockEntity";

export default props => {
  const classes = useStyles();
  const entities = useSelector(state => state.homeassistant.entities, []) || {};
  return (
    <div className={classes.container}>
      <ClockEntity className={classes.clock} />
      {
        Object.values(entities).map((entity, index) => {
          const EntityComponent = getEntityComponent(entity);
          return EntityComponent && <EntityComponent entity={entity} key={index} />
        })
      }
    </div>
  );
}

const useStyles = makeStyles(theme => {
  return {
    container: {
      display: 'grid',
      gridTemplateColumns: 'repeat(auto-fill, minmax(12rem, 1fr))',
      gridAutoRows: '1fr',
      gridGap: 8,
      '&::before': {
        content: "''",
        width: 0,
        paddingBottom: '100%',
        gridRow: '1 / 1',
        gridColumn: '1 / 1'
      },
      '& > *:first-child': {
        gridRow: '1 / 1',
      //   gridColumn: '1 / 1'
      }
    },
    clock: {
      gridColumn: '1 / 3'
    }
  }
});
