import { PropTypes, Component } from 'react';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import NavigationHeader from './../components/NavigationHeader';

class App extends Component {
  static propTypes = {
    children: PropTypes.node,
  }

  render() {
    return (
      <MuiThemeProvider muiTheme={getMuiTheme()}>
        <div>
          <NavigationHeader />
          {this.props.children}
        </div>
      </MuiThemeProvider>
    );
  }
}

export default App;
