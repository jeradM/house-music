import { UPDATE_WS } from "../actions/types";

const initialState = null;

export default function(state = initialState, action) {
  switch (action.type) {
    case UPDATE_WS:
      return action.payload;
    default:
      return state;
  }
}
