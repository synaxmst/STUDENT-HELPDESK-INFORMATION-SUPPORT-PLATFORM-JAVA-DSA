import java.util.Scanner;

/* =========================================================================
 * STUDENT HELPDESK PLATFORM (ADVANCED DSA EDITION)
 * =========================================================================
 * Description: A console-based application mimicking the core features of 
 * the Student Helpdesk Website using pure Data Structures and Algorithms.
 * 
 * CO5/CO6: Complete Practical Real-World Application of Linear DS.
 * =========================================================================
 */

// =========================================================================
// CO4: HASH-BASED DATA STRUCTURES (Java Collections - HashMap)
// Application: O(1) Quick Retrieval of Student Ticket History Count
// =========================================================================
import java.util.HashMap;

// =========================================================================
// CO2: CIRCULAR LINKED LIST
// Application: Support Agent Assignment (Round Robin Contact Routing)
// =========================================================================
class AgentNode {
    String agentName;
    String department;
    AgentNode next;

    AgentNode(String name, String dept) {
        this.agentName = name;
        this.department = dept;
    }
}

class ContactRotator {
    AgentNode head = null;
    AgentNode tail = null;
    AgentNode currentPointer = null;

    void addAgent(String name, String dept) {
        AgentNode newNode = new AgentNode(name, dept);
        if (head == null) {
            head = newNode;
            tail = newNode;
            newNode.next = head;
            currentPointer = head;
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head;
        }
    }

    void assignNextAgent() {
        if (currentPointer != null) {
            System.out.println("Ticket Routed Automatically to: " + currentPointer.agentName + " (" + currentPointer.department + ")");
            currentPointer = currentPointer.next; // Move to next in circle
        }
    }
}

// =========================================================================
// CO3: HEAPS & PRIORITY QUEUES (Max Heap Array Implementation)
// Application: Real-world Submission workflow prioritizing urgent tickets
// =========================================================================
class Ticket {
    int id;
    String issue;
    int priority; // 100 = Critical, 50 = High, 10 = Low

    Ticket(int id, String issue, int priority) {
        this.id = id;
        this.issue = issue;
        this.priority = priority;
    }
}

class PriorityTicketHeap {
    Ticket[] heap;
    int size;

    PriorityTicketHeap(int capacity) {
        heap = new Ticket[capacity];
        size = 0;
    }

    private void swap(int i, int j) {
        Ticket temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    void insert(Ticket ticket) {
        if (size == heap.length) {
            System.out.println("Ticket Server Overloaded!");
            return;
        }
        heap[size] = ticket;
        int current = size;
        size++;

        // Bubble Up Max Heap
        while (current > 0 && heap[current].priority > heap[(current - 1) / 2].priority) {
            swap(current, (current - 1) / 2);
            current = (current - 1) / 2;
        }
        System.out.println("Query Ticket #" + ticket.id + " Submitted to Queue!");
    }

    Ticket extractMax() {
        if (size == 0) return null;
        Ticket root = heap[0];
        heap[0] = heap[size - 1];
        size--;

        // Max Heapify Down
        int i = 0;
        while (i * 2 + 1 < size) {
            int left = i * 2 + 1;
            int right = i * 2 + 2;
            int largest = left;

            if (right < size && heap[right].priority > heap[left].priority) {
                largest = right;
            }
            if (heap[i].priority >= heap[largest].priority) break;

            swap(i, largest);
            i = largest;
        }
        return root;
    }

    void displayQueue() {
        if (size == 0) {
            System.out.println("No pending queries in Queue.");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println("[Priority: " + heap[i].priority + "] Ticket #" + heap[i].id + ": " + heap[i].issue);
        }
    }
}

// =========================================================================
// CO2: DOUBLY LINKED LIST
// Application: Comprehensive Ticket Log (Forward/Reverse View History)
// =========================================================================
class LogNode {
    Ticket ticket;
    LogNode prev, next;

    LogNode(Ticket t) {
        this.ticket = t;
    }
}

class TicketLogList {
    LogNode head, tail;

    void addLog(Ticket t) {
        LogNode newNode = new LogNode(t);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    void viewHistoryForward() {
        LogNode curr = head;
        if (curr == null) { System.out.println("Notice: No closed tickets."); return; }
        System.out.println("\n--- Ticket Resolution History (Oldest to Newest) ---");
        while (curr != null) {
            System.out.println("Closed Ticket #" + curr.ticket.id + " - " + curr.ticket.issue);
            curr = curr.next;
        }
    }

    void viewHistoryReverse() {
        LogNode curr = tail;
        if (curr == null) { System.out.println("Notice: No closed tickets."); return; }
        System.out.println("\n--- Ticket Resolution History (Newest to Oldest) ---");
        while (curr != null) {
            System.out.println("Closed Ticket #" + curr.ticket.id + " - " + curr.ticket.issue);
            curr = curr.prev;
        }
    }
}

// =========================================================================
// CO3: STACKS (LIFO)
// Application: Undo Feature for accidentally resolved tickets
// =========================================================================
class UndoStack {
    private Ticket[] stack;
    private int top;

    UndoStack(int capacity) {
        stack = new Ticket[capacity];
        top = -1;
    }

    void push(Ticket t) {
        if (top < stack.length - 1) stack[++top] = t;
    }

    Ticket pop() {
        if (top >= 0) return stack[top--];
        return null;
    }
}

// =========================================================================
// CO1: ALGORITHMIC EFFICIENCY (Binary Search)
// Application: Fast Database Lookup for an FAQ ID
// =========================================================================
class FAQDatabase {
    int[] faqIDs = {1, 2, 3, 4, 5, 6}; // Pre-sorted for Binary Search
    String[] questions = {
        "1. How do I reset my student portal password?",
        "2. When is the deadline for course registration?",
        "3. How can I apply for a hostel room?",
        "4. How long does it take for a query to be resolved?",
        "5. Can I visit the helpdesk office physically?",
        "6. What should I do if my student ID card is lost?"
    };
    String[] answers = {
        "To reset your password, click on the \"Forgot Password\" link on the portal login page. A password reset link will be sent to your registered college email address.",
        "Course registration deadlines vary by semester. Please check the academic calendar available on the main college website for the specific dates under the \"Important Dates\" section.",
        "Hostel applications can be submitted through your student dashboard under the \"Accommodation\" tab. Ensure you have paid the prerequisite semester fees before applying.",
        "Standard queries are usually resolved within 24-48 business hours. During peak times, it might take up to 3 business days.",
        "Yes, the physical helpdesk office is located in the Main Administrative Block, Room 102. Visiting hours are Monday to Friday, 9:00 AM to 5:00 PM.",
        "Report the lost card immediately by submitting an administrative query online. Then visit the security office with a valid government ID to issue a replacement card. A replacement fee applies."
    };

    void searchFAQ(int id) {
        int left = 0, right = faqIDs.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (faqIDs[mid] == id) {
                System.out.println("\nFAQ Found O(log n):");
                System.out.println("Q: " + questions[mid]);
                System.out.println("A: " + answers[mid]);
                return;
            }
            if (faqIDs[mid] < id) left = mid + 1;
            else right = mid - 1;
        }
        System.out.println("\nFAQ ID not found.");
    }
    
    void printAll() {
        System.out.println("\n--- Frequently Asked Questions ---");
        for (String q: questions) {
            System.out.println(q);
        }
    }
}

// =========================================================================
// BASIC LINEAR DATA STRUCTURES
// Application: Basic FIFO Queue and Singly Linked List
// =========================================================================

class BasicQueue {
    String[] queue;
    int front = 0;
    int rear = 0;

    BasicQueue(int capacity) {
        queue = new String[capacity];
    }

    void enqueue(String item) {
        if (rear == queue.length) {
            System.out.println("Basic Queue is full.");
            return;
        }
        queue[rear++] = item;
        System.out.println("General Inquiry logged: " + item);
    }

    void dequeue() {
        if (front == rear) {
            System.out.println("No general inquiries pending.");
            return;
        }
        System.out.println("Processed Inquiry: " + queue[front++]);
    }
}

class SinglyNode {
    String data;
    SinglyNode next;

    SinglyNode(String data) {
        this.data = data;
        this.next = null;
    }
}

class SinglyLinkedList {
    SinglyNode head;

    void insert(String data) {
        SinglyNode newNode = new SinglyNode(data);
        if (head == null) {
            head = newNode;
        } else {
            SinglyNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        System.out.println("Saved to feedback log: " + data);
    }

    void display() {
        if (head == null) {
            System.out.println("Feedback log is empty.");
            return;
        }
        System.out.println("\n--- Agent Feedback Log ---");
        SinglyNode current = head;
        while (current != null) {
            System.out.println("-> " + current.data);
            current = current.next;
        }
    }
}

// =========================================================================
// MAIN APPLICATION ENGINE
// =========================================================================
public class HelpdeskPlatform {
    static Scanner sc = new Scanner(System.in);
    
    // Core Modules
    static PriorityTicketHeap ticketQueue = new PriorityTicketHeap(100);
    static TicketLogList ticketLog = new TicketLogList();
    static ContactRotator contactAgents = new ContactRotator();
    static UndoStack undoStack = new UndoStack(50);
    static FAQDatabase faqDatabase = new FAQDatabase();
    
    // Basic Examples
    static BasicQueue basicQueue = new BasicQueue(10);
    static SinglyLinkedList basicLL = new SinglyLinkedList();
    
    // CO4: Hash Map to maintain O(1) lookup for user query counts
    static HashMap<String, Integer> studentQueryCount = new HashMap<>();
    
    static int ticketCounter = 1000;

    public static void main(String[] args) {
        // Pre-fill Agents
        contactAgents.addAgent("Alice System", "IT Support");
        contactAgents.addAgent("Bob Admin", "Administrative");
        contactAgents.addAgent("Charlie Academics", "Academic Lead");

        System.out.println("**************************************************");
        System.out.println("* STUDENT HELPDESK PLATFORM (ADVANCED DSA MODEL) *");
        System.out.println("**************************************************");

        while (true) {
            showMainMenu();
        }
    }

    /* 
     * =======================================================================
     * INSTRUCTOR REFERENCE / VIVA DEFENSE GUIDE (DO NOT PRINT TO CONSOLE)
     * =======================================================================
     * 1. Submit Helpdesk Query        -> CO3: Priority Queue (Max Heap)
     * 2. Display Pending Queries      -> CO3: Max Heap Traversal
     * 3. Admin: Resolve Top Query     -> CO3: Heap Extract-Max Algorithm
     * 4. Admin: Undo Last Resolve     (Stack LIFO)
     * 5. View Query History           -> CO2: Doubly Linked List Traversal
     * 6. Browse FAQs                  -> Arrays structure
     * 7. Search Specific FAQ ID       -> CO1: Binary Search O(log n)
     * 8. Look Up Student Query Count  -> CO4: Hash Map (O(1) Access)
     * 9. Submit General Inquiry       -> CO2: Basic Queue (Linear FIFO)
     * 10. Submit Agent Feedback       -> CO2: Singly Linked List Insertion
     * =======================================================================
     */
    private static void showMainMenu() {
        System.out.println("\n=========== DASHBOARD ===========");
        System.out.println("1. Submit Helpdesk Query");
        System.out.println("2. Display Pending Queries");
        System.out.println("3. Admin: Resolve Top Query");
        System.out.println("4. Admin: Undo Last Resolve");
        System.out.println("5. View Query History");
        System.out.println("6. Browse FAQs");
        System.out.println("7. Search Specific FAQ ID");
        System.out.println("8. Look Up Student Query Count");
        System.out.println("9. Submit General Inquiry");
        System.out.println("10. Submit Agent Feedback");
        System.out.println("11. Exit Platform");
        System.out.print("\nChoice: ");
        
        int choice;
        try {
            choice = sc.nextInt();
            sc.nextLine(); // consume newline
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a valid number.");
            sc.nextLine(); // clear invalid input
            return;
        }

        switch (choice) {
            case 1: submitQuery(); break;
            case 2:
                System.out.println("\n--- Live priority Pipeline ---");
                ticketQueue.displayQueue();
                break;
            case 3: resolveTopQuery(); break;
            case 4: undoLastResolve(); break;
            case 5: viewQueryHistory(); break;
            case 6: faqDatabase.printAll(); break;
            case 7: searchFAQ(); break;
            case 8: lookupStudentQueryCount(); break;
            case 9:
                System.out.print("Enter General Inquiry (Non-urgent): ");
                String inquiry = sc.nextLine();
                basicQueue.enqueue(inquiry);
                System.out.println("-> Inquiry submitted successfully!");
                break;
            case 10:
                System.out.print("Enter Feedback for Agent: ");
                String feedback = sc.nextLine();
                basicLL.insert(feedback);
                System.out.println("-> Feedback submitted successfully!");
                basicLL.display();
                break;
            case 11:
                System.out.println("Shutting down... Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid entry. Please try again.");
        }
    }

    private static void submitQuery() {
        System.out.print("Enter Your Email: ");
        String email = sc.nextLine();
        
        if (!email.contains("@")) {
            System.out.println("Error: Invalid Email! Must contain an '@' symbol. Ticket submission cancelled.");
            return;
        }

        System.out.print("Enter Issue Description: ");
        String issue = sc.nextLine();
        
        int urgency;
        try {
            System.out.print("Urgency Level (100=Critical, 50=High, 10=Low): ");
            urgency = sc.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Invalid Input! Urgency Level must be a number (e.g., 100, 50, 10). Ticket submission cancelled.");
            return;
        } finally {
            sc.nextLine(); // Clear scanner buffer
        }
        
        ticketQueue.insert(new Ticket(++ticketCounter, issue, urgency));
        contactAgents.assignNextAgent(); // Rotate Contact
        
        // Add to Hash Map
        studentQueryCount.put(email, studentQueryCount.getOrDefault(email, 0) + 1);
    }

    private static void resolveTopQuery() {
        Ticket resolved = ticketQueue.extractMax();
        if (resolved != null) {
            System.out.println("\nResolved Critical Ticket #" + resolved.id + " (" + resolved.issue + ")");
            ticketLog.addLog(resolved); // Add to History LL
            undoStack.push(resolved); // Add to Undo Stack
        } else {
            System.out.println("Queue is empty.");
        }
    }

    private static void undoLastResolve() {
        Ticket reverted = undoStack.pop();
        if (reverted != null) {
            System.out.println("\nUndoing Resolution! Returning Ticket #" + reverted.id + " to queue.");
            ticketQueue.insert(reverted);
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    private static void viewQueryHistory() {
        System.out.print("View [1] Oldest First or [2] Newest First? ");
        int order = sc.nextInt();
        sc.nextLine();
        if (order == 1) ticketLog.viewHistoryForward();
        else ticketLog.viewHistoryReverse();
    }

    private static void searchFAQ() {
        System.out.print("Enter exact FAQ ID to search (e.g. 1, 2, 3): ");
        int searchId = sc.nextInt();
        sc.nextLine();
        faqDatabase.searchFAQ(searchId);
    }

    private static void lookupStudentQueryCount() {
        System.out.print("Enter Email to search: ");
        String searchEmail = sc.nextLine();
        if (studentQueryCount.containsKey(searchEmail)) {
            System.out.println("Student " + searchEmail + " has submitted " + studentQueryCount.get(searchEmail) + " queries.");
        } else {
            System.out.println("No records found for " + searchEmail + ".");
        }
    }
}
