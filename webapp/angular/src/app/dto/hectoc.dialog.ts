export interface HectocDialogDto {
  challenge: String;
  solution: String;
  valid: Boolean;
  time: DOMHighResTimeStamp;
  result?: Number;
  error?: any;
}
