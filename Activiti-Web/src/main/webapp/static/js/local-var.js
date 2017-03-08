var STUDENT_ASK_FOR_LEAVE = "student-ask-for-leave";
var STUDENT_ASK_FOR_LEAVE_TEACHER_AUDIT = "teacher_audit";

var AuditPageMapping = {
    "student-ask-for-leave": {
        "teacher_audit": "teacher-audit-student-ask-for-leave"
    }
};
function getUserTaskPageName(processDefinitionKey, taskDefinitionKey) {
    var processDefinitionTaskMapping = AuditPageMapping[processDefinitionKey];
    if (processDefinitionTaskMapping) {
        return processDefinitionTaskMapping[taskDefinitionKey];
    }
    return undefined;
}