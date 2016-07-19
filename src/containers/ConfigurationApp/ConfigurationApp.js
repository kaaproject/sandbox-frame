import { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import Immutable from 'immutable';
import radium from 'radium';
import pureRender from 'pure-render-decorator';

import { getFilteredApps } from './../../selectors/apps';
import { filterApps } from './../../actions/apps';

const styles = {
  base: {
    display: 'flex',
    justifyContent: 'center',
    flexWrap: 'wrap',
    padding: 20,
  },
};

@radium
@pureRender
class ConfigurationApp extends Component {
  static propTypes = {
    apps: PropTypes.instanceOf(Immutable.Map),
    filterApps: PropTypes.func.isRequired,
  }

  componentWillMount() {
    this.props.filterApps();
  }

  renderApps() {
    return null;
  }

  render() {
    return (
      <div style={[styles.base]}>
        {'Configuration of app'}
        {this.renderApps()}
      </div>
    );
  }
}

const mapStateToProps = (state, props) => ({
  apps: getFilteredApps(state, props),
});

export default connect(mapStateToProps, {
  filterApps,
})(ConfigurationApp);
