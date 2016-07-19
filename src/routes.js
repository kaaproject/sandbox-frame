import { Route, IndexRedirect } from 'react-router';

import App from './containers/App';
import StartPage from './containers/StartPage';
import Management from './containers/Management';
import Application from './containers/Application';
import ConfigurationApp from './containers/ConfigurationApp';

export default (
  <Route
    component={App}
    path="/"
  >
    <IndexRedirect to="/sandbox" />
    <Route
      component={StartPage}
      path="/sandbox"
    />
    <Route
      component={Management}
      path="/sandbox/management"
    />
    <Route
      component={Application}
      path="/sandbox/:name"
    />
    <Route
      component={ConfigurationApp}
      path="/sandbox/:name/:name"
    />
  </Route>
);
