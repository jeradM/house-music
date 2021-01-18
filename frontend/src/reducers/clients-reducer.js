import { CLIENTS_UPDATED } from "../actions/types";

const initialState = [];

export default (state = initialState, action) => {
  switch (action.type) {
    case CLIENTS_UPDATED:
      return action.payload;
    default:
      return state;
  }
}
