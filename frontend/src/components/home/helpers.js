import { WATCHED_ENTITIES } from "../../reducers/homeassistant-reducer";
import LightEntity from "./entities/LightEntity";
import ClimateEntity from "./entities/ClimateEntity";

export function getEntityComponent(entity) {
  if (!entity) return null;
  const type = WATCHED_ENTITIES[entity.entity_id];
  switch (type) {
    case 'Light':
      return LightEntity;
    case 'Climate':
      return ClimateEntity;
    default:
      return null;
  }
}
