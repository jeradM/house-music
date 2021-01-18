import { createMuiTheme } from '@material-ui/core/styles';
import { blue, blueGrey, cyan, deepOrange, orange, yellow } from "@material-ui/core/colors";

const theme = createMuiTheme({
  palette: {
    primary: blueGrey,
    secondary: {
      main: cyan[50]
    }
  },
  typography: {
    useNextVariants: true,
  },
  grid: {
    spacing: 1
  },
  alarm: {
    blue: blue[900],
    yellow: yellow[900],
    orange: orange[900],
    red: deepOrange[900]
  }
});

export default theme;
