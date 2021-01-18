import { STREAMS_UPDATED } from "../actions/types";

const initialState = [];

export default function(state = initialState, action) {
  switch (action.type) {
    case STREAMS_UPDATED:
      return action.payload;
    default:
      return state;
  }
}
