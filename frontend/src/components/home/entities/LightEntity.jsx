import React from 'react';
import BaseEntity from "./BaseEntity";
import { Lightbulb } from "mdi-material-ui";

export default props => {
  return (
    <BaseEntity entity={props.entity}>
      <Lightbulb />
    </BaseEntity>
  )
}
