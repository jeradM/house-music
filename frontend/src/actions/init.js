import {
  CLIENTS_UPDATED, CONFIG_UPDATED,
  ENTITIES_UPDATED,
  HASS_CONN_UPDATED,
  STREAMS_UPDATED,
  UPDATE_WS
} from "./types";
import { Stomp } from "@stomp/stompjs";
import { Auth, createConnection, entitiesColl } from "home-assistant-js-websocket";
import { SERVER_URL, WS_URL } from "../config";

export const init = () => (dispatch, getState) => {
  const fetchConfig = async () => {
    const resp = await fetch(SERVER_URL + '/config/');
    return resp.json();
  };
  fetchConfig().then(config => {
    dispatch({type: CONFIG_UPDATED, payload: config});
    initWs(dispatch);
    initHass(dispatch, config);
  })
};

const initWs = async (dispatch) => {
  const ws = Stomp.client(WS_URL);
  ws.debug = () => null;
  ws.reconnect_delay = 10000;
  ws.connect({}, () => {
    ws.subscribe("/topic/clients", msg => onClients(msg, dispatch));
    ws.subscribe("/topic/streams", msg => onStreams(msg, dispatch));
    ws.subscribe("/app/init", msg => onInit(msg, dispatch));
    dispatch({
      type: UPDATE_WS,
      payload: ws
    });
  });
};

const initHass = async (dispatch, config) => {
  const authOpts = {
    hassUrl: config.hassUrl,
    access_token: config.hassAccessToken,
    expires: new Date(new Date().getTime() + 1e11)
  };
  const auth = new Auth(authOpts);
  const connection = await createConnection({ auth });
  dispatch({type: HASS_CONN_UPDATED, payload: connection});
  const coll = entitiesColl(connection);
  await coll.refresh();
  coll.subscribe(entities => {
    dispatch({type: ENTITIES_UPDATED, payload: entities});
  });
};

const onClients = (msg, dispatch) => {
  const data = JSON.parse(msg.body);
  dispatch({ type: CLIENTS_UPDATED, payload: data.clients });
};

const onStreams = (msg, dispatch) => {
  const data = JSON.parse(msg.body);
  dispatch({ type: STREAMS_UPDATED, payload: data.streams });
};

const onInit = (msg, dispatch) => {
  const data = JSON.parse(msg.body);
  if (data.clients && data.clients.length > 0)
    dispatch({ type: CLIENTS_UPDATED, payload: data.clients });
  if (data.streams && data.streams.length > 0)
    dispatch({ type: STREAMS_UPDATED, payload: data.streams });
};
