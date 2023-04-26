export const COMMENTS_TABLE_COLUMNS = [
  { id: "date", label: "Date" },
  { id: "userEmail", label: "User" },
  { id: "text", label: "Comment" },
];

export const HISTORY_TABLE_COLUMNS = [
  { id: "date", label: "Date" },
  { id: "email", label: "User" },
  { id: "action", label: "Action" },
  { id: "description", label: "Description" },
];

export const TICKETS_TABLE_COLUMNS = [
  { id: "id", label: "ID", align: "left" },
  { id: "name", label: "Name", align: "left" },
  { id: "desiredResolutionDate", label: "Desired Date" },
  { id: "urgencyName", label: "Urgency", align: "left" },
  { id: "status", label: "Status", align: "left" },
  { id: "actions", label: "Action", align: "center" },
];
