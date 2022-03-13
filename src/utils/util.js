export const formatDate = (date, formatStr) => {
  let tempDate = new Date(date);
  let year = tempDate.getFullYear();
  let month = tempDate.getMonth();
  month+1>=10 && (month = (month + 1));
  month < 10 && (month = '0' + (month + 1));
  let day = tempDate.getDate();
  day < 10 && (day = '0' + day);
  let hour = tempDate.getHours();
  hour < 10 && (hour = '0' + hour);
  let minute = tempDate.getMinutes();
  minute < 10 && (minute = '0' + minute);
  let tempStr = formatStr || "yyyy-mm-dd";
  let formatDate  = tempStr.replace("yyyy", year).replace("mm", month).replace("dd", day).replace("h", hour).replace("m", minute);
  return formatDate;
}