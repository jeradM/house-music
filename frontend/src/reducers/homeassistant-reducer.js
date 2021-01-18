import { ENTITIES_UPDATED, HASS_CONN_UPDATED } from "../actions/types";

const initialState = {
  connection: null,
  entities: {}
};

export const WATCHED_ENTITIES = {
  'light.living_room_light': 'Light',
  'switch.nursery_light_switch': 'Light',
  'switch.upstairs_light': 'Light',
  'weather.ecobee': null
};

export const CAMERA_ENTITIES = [
  'camera.front_porch',
  'camera.driveway',
  'camera.nursery',
  'camera.dog_run',
  'camera.side_yard',
  'camera.back_yard'
];

export const ALARM_ENTITY = 'alarm_control_panel.ha_alarm';

const ALL_ENTITIES = [...Object.keys(WATCHED_ENTITIES), ...CAMERA_ENTITIES, ALARM_ENTITY];

export default (state = initialState, action) => {
  switch (action.type) {
    case HASS_CONN_UPDATED:
      return {
        ...state,
        connection: action.payload
      };
    case ENTITIES_UPDATED:
      return {
        ...state,
        entities: Object.keys(action.payload)
          .filter(k => ALL_ENTITIES.includes(k))
          .reduce((obj, key) => {
            return {
              ...obj,
              [key]: action.payload[key]
            }
          }, {})
      };
    default:
      return state;
  }
}
