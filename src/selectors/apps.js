import { createSelector } from 'reselect';

// TODO in future this logic should be changed to get data by query from location
const getFilteredAppsList = (state) => state.getIn(['entities', 'apps']);

export const getFilteredApps = createSelector(
  [getFilteredAppsList],
  (apps) => apps
);
