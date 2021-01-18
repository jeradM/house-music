import React from 'react';
import BaseEntity from "./BaseEntity";
import { Thermometer } from "mdi-material-ui";

export default props => {
  return (
    <BaseEntity entity={props.entity}>
      <Thermometer />
    </BaseEntity>
  )
}
