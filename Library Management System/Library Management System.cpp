#include <bits/stdc++.h>
using namespace std;
// ---------- Structures ----------
struct Book {
    string name, author;
    Book() {}
    Book(string name, string author) {
        this->name = name;
        this->author = author;
    }
};
struct User {
    string ID, name;
    User() {}
    User(string ID, string name) {
        this->ID = ID;
        this->name = name;
    }
};

// ---------- Linked list for Users ----------
struct Node {
    User user;
    Node* link;
    Node(User u) : user(u), link(nullptr) {}
};

// ---------- Global Data ----------
unordered_map<string, Book> library;             // Code -> Book
unordered_map<string, int> availability;         // Code -> Count
unordered_map<string, vector<string>> bookTaken; // UserID -> list of codes
Node* userHead = nullptr;

// ---------- Helper Functions ----------
bool userExists(const string& id) {
    Node* temp = userHead;
    while (temp) {
        if (temp->user.ID == id) return true;
        temp = temp->link;
    }
    return false;
}

void addUser(const string& id, const string& name) {
    Node* newNode = new Node(User(id, name));
    newNode->link = userHead;
    userHead = newNode;
}

void addBook() {
    string code, title, author;
    int count;
    cout << "Enter Book Code: ";
    cin >> code;
    cout << "Enter Book Title: ";
    cin.ignore();
    getline(cin, title);
    cout << "Enter Author: ";
    getline(cin, author);
    cout << "Enter No. of Copies: ";
    cin >> count;

    library[code] = Book(title, author);
    availability[code] = count;

    cout << "Book added successfully!\n";
}

void searchBook() {
    string code;
    cout << "Enter Book Code to search: ";
    cin >> code;

    if (library.find(code) != library.end()) {
        cout << "Title: " << library[code].name << "\nAuthor: " << library[code].author << "\nAvailable Copies: " << availability[code] << "\n";
    } 
    else {
        cout << "Book not found!\n";
    }
}

void issueBook(const string& userID) {
    string code;
    cout << "Enter Book Code to issue: ";
    cin >> code;

    if (library.find(code) == library.end()) {
        cout << "Book not found in library!\n";
        return;
    }

    if (availability[code] > 0) {
        availability[code]--;
        bookTaken[userID].push_back(code);
        cout << "Book issued successfully!\n";
    } 
    else {
        cout << "Book currently unavailable!\n";
    }
}

void returnBook(const string& userID) {
    string code;
    cout << "Enter Book Code to return: ";
    cin >> code;

    auto& books = bookTaken[userID];
    auto it = find(books.begin(), books.end(), code);

    if (it != books.end()) {
        books.erase(it);
        availability[code]++;
        cout << "Book returned successfully!\n";
    } else {
        cout << "You haven't borrowed this book.\n";
    }
}
void viewBorrowed(const string& userID) { //
    auto& books = bookTaken[userID];
    if (books.empty()) {
        cout << "No books borrowed.\n";
        return;
    }
    cout << "Borrowed Books:\n";
    for (auto& code : books) {
        cout << "- " << library[code].name << " (" << code << ")\n";
    }
}
void userMenu(const string& id) { // Nested Menu Driven Program for User
    int choice;
    do {
        cout << "\n---- User Menu ----\n" << "1. Issue Book\n2. Return Book\n3. Search Book\n4. View Borrowed Books\n5. Exit User Menu\n" << "Enter choice: ";
        cin >> choice;
        switch (choice) {
            case 1: issueBook(id); break;
            case 2: returnBook(id); break;
            case 3: searchBook(); break;
            case 4: viewBorrowed(id); break;
            case 5: cout << "Returning to main menu...\n"; break;
            default: cout << "Invalid choice!\n";
        }
    } while (choice != 5);
}
int main() {
    int choice;
    while (true) { // Menu for LMS (Main Menu)
        cout << "\n===== Library Management System =====\n" << "1. Add Book (Admin)\n2. User Login\n3. Exit\nEnter choice: ";
        cin >> choice;
        if (choice == 1) {
            addBook();
        }
        else if (choice == 2) {
            string id, name;
            cout << "Enter User ID: ";
            cin >> id;
            if (!userExists(id)) {
                cout << "New user! Enter your name: ";
                cin.ignore();
                getline(cin, name);
                addUser(id, name);
            }
            userMenu(id);
        }
        else if (choice == 3) {
            cout << "Exiting...\n";
            break;
        } 
        else {
            cout << "Invalid choice!\n";
        }
    }
    return 0;
}