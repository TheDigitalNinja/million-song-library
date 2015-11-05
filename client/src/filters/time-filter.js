/**
 * Time filter for display purposes
 * @param {string} input
 * @param {string} format 'HH:MM:SS'
 * @returns {Function}
 */
export default function timeFilter() {
  return function (input, format) {
    try {
      const secNum = parseInt(input, 10);
      let hours = Math.floor(secNum / 3600);
      let minutes = Math.floor((secNum - (hours * 3600)) / 60);
      let seconds = secNum - (hours * 3600) - (minutes * 60);

      if(hours < 10) {
        hours = `0${hours}`;
      }
      if(minutes < 10) {
        minutes = `0${minutes}`;
      }
      if(seconds < 10) {
        seconds = `0${seconds}`;
      }

      let result = '';

      switch(format) {
        case 'MM:SS':
          result = `${minutes}:${seconds}`;
          break;
        case 'HH:MM:SS':
          result = `${hours}:${minutes}:${seconds}`;
          break;
        default:
          result = `${minutes}:${seconds}`;
          break;
      }
      return result;

    } catch(err) {
      return input;
    }
  };
}
