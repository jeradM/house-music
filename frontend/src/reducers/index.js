import { combineReducers } from "redux";
import clientsReducer from './clients-reducer';
import streamsReducer from './streams-reducer';
import wsReducer from './websocket-reducer';
import modalReducer from './modal-reducer';
import homeassistantReducer from './homeassistant-reducer';
import configReducer from './config-reducer';

export default combineReducers({
  ws: wsReducer,
  clients: clientsReducer,
  streams: streamsReducer,
  homeassistant: homeassistantReducer,
  modal: modalReducer,
  config: configReducer
});
