-- ============================================================
-- OnboardAssist Sample Knowledge Base
-- Run AFTER schema.sql and after running the application once
-- to insert sample onboarding knowledge for RAG retrieval
-- ============================================================

-- Insert sample knowledge documents
INSERT INTO knowledge_documents (title, content, source) VALUES
(
    'Induction Program Overview',
    'The induction program is a mandatory onboarding process for all new joiners, trainees, and interns. The induction period typically lasts for the first 30 days of joining. During induction, new employees are introduced to the organization, its culture, values, and key processes. The induction program includes orientation sessions, departmental introductions, and mandatory training modules. All new joiners must attend the induction session scheduled in their first week. The HR team sends a calendar invite with the induction schedule before your joining date. If you miss the induction session, contact HR to schedule a makeup session.',
    'HR Onboarding Policy'
),
(
    'Mandatory Training Courses',
    'All new joiners must complete mandatory training courses within the first 30 days of joining. Mandatory training is available on the Learning Portal. To access mandatory training: 1. Log in to the Learning Portal at learningportal.company.com. 2. Click on "My Learning" in the top navigation. 3. Navigate to the "Mandatory Training" section. 4. You will see all assigned mandatory courses listed there. 5. Click on a course to begin. 6. Complete all modules within the course. 7. Courses must be completed before the specified due date shown on each course card. Mandatory courses typically include: Code of Conduct, Information Security Awareness, Anti-Harassment Policy, Data Privacy Guidelines, and Safety at Workplace.',
    'Learning Portal Guide'
),
(
    'Assessment Policy and Passing Criteria',
    'All mandatory training courses include a final assessment at the end. The minimum passing score required to clear any assessment is 70 percent. If you score below 70 percent, you will be required to retake the assessment. You can retake the assessment up to 3 times. If you fail all 3 attempts, please contact your Learning and Development coordinator for assistance. Assessment results are automatically recorded in the system. Your manager will be notified of your assessment completion status. All assessments must be completed within the same deadline as the training course. Assessments are multiple choice and typically contain 10 to 20 questions.',
    'Assessment Policy Document'
),
(
    'Learning Portal Access',
    'The Learning Portal is the centralized platform for all training and learning activities. URL: learningportal.company.com. Your login credentials for the Learning Portal are the same as your company email credentials. If you cannot log in to the Learning Portal: 1. Ensure you are using your company email address (yourname@company.com). 2. Use your network/Windows login password. 3. If you forgot your password, click Forgot Password on the login page. 4. If the issue persists, raise a ticket with the IT Helpdesk at it-helpdesk@company.com or call extension 1234. The Learning Portal is accessible from any device including mobile phones and tablets.',
    'IT Setup Guide'
),
(
    'Software Installation for New Joiners',
    'The following software must be installed on your workstation: 1. Visual Studio Code or IntelliJ IDEA (development IDE). 2. Git (version control). 3. Java Development Kit 17 or higher. 4. Node.js version 18 or higher. 5. Docker Desktop. 6. Postman (API testing). 7. Microsoft Teams (communication). 8. VPN Client (for remote access). All software can be downloaded from the Software Center. To access Software Center: Open Start Menu and search for Software Center. Browse the catalog and install required software. Some software requires admin approval - submit a request and it will be installed within 24 hours. If you face installation issues, contact IT Helpdesk.',
    'IT Setup Guide'
),
(
    'Document Verification Requirements',
    'All new joiners must submit the following documents for verification within the first 3 days of joining: 1. Government-issued photo ID (Aadhaar Card, Passport, or Driving License). 2. PAN Card. 3. Educational certificates (highest qualification). 4. Previous employment experience letters (for experienced hires). 5. Bank account details for salary processing. 6. Passport-size photographs (4 copies). 7. Address proof (utility bill or bank statement). Documents can be submitted in person to the HR team or via email to hr-documents@company.com. Originals must be brought for verification; self-attested photocopies will be retained. Failure to submit documents on time may delay your salary processing.',
    'HR Document Policy'
),
(
    'Project Onboarding Process',
    'After completing the induction and mandatory training, new joiners are assigned to a project. The project onboarding process involves: 1. Meeting with your reporting manager for a project introduction. 2. Getting access to project repositories and tools. 3. Reviewing project documentation and architecture. 4. Attending sprint/team meetings. 5. Completing any project-specific training. Project access is provisioned by the IT team upon request from your manager. To get project access: Your manager raises an IT access request. IT team provisions access within 48 hours. You receive an email confirmation with access details. If you do not receive project access within 3 days, follow up with your manager.',
    'Project Onboarding Guide'
),
(
    'Email and Communication Setup',
    'Your company email account is created before your joining date. You will receive your email credentials from HR. To access your email: Open Outlook or go to mail.company.com in a browser. Login with your company email and initial password. Change your password immediately on first login. Set up your email signature with your name, designation, and contact number. Company email is used for all official communications. Microsoft Teams is the primary chat and video conferencing tool. You can download Teams from the Software Center or the official Microsoft website. Your Teams account is automatically linked to your company email.',
    'IT Communication Setup Guide'
),
(
    'Leave Policy for New Joiners',
    'New joiners are entitled to leave from their date of joining. Leave types available: 1. Casual Leave: 12 days per year (prorated based on joining month). 2. Sick Leave: 12 days per year. 3. Privilege Leave: 15 days per year (earned after 6 months). To apply for leave: 1. Log in to the HR Portal at hrportal.company.com. 2. Click on Leave Management. 3. Select the leave type and dates. 4. Add a reason for the leave. 5. Submit for manager approval. Leave must be applied at least 24 hours in advance (except sick leave). Emergency leave can be applied retrospectively within 2 working days with manager approval.',
    'HR Leave Policy'
),
(
    'IT Helpdesk and Support',
    'For any IT-related issues, contact the IT Helpdesk: Email: it-helpdesk@company.com. Phone: Extension 1234 (internal) or +91-XXXXXXXXXX (external). Self-service portal: itsupport.company.com. Helpdesk hours: Monday to Friday, 8 AM to 8 PM. For urgent issues outside helpdesk hours, call the emergency IT support number provided in your onboarding email. Common issues handled by IT Helpdesk: Password reset, Software installation, Email setup, VPN configuration, Laptop hardware issues, Application access requests. Please raise a ticket for every issue to ensure proper tracking and resolution within SLA.',
    'IT Support Guide'
);

-- Now insert chunks for each document
-- Note: In production, these are generated automatically by the application
-- These sample chunks will need embeddings generated by the application
-- Run the application first, then use the Knowledge API to add documents programmatically
-- This file is for reference only
