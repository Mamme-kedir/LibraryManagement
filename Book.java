//Book.Java 
//Represents a book in the library system 


public class Book {
    //Fields (attributes)

    private String title;
    private String author;
    private String isbn;
    private int yearPublished;

    //constructor - used to initialize a new Book object 
    public Book(String title, String author , String isbn, int yearPublished) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.yearPublished = yearPublished;
    }

    //Method to display book information 
    public void displayInfo() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + isbn);
        System.out.println("Year Published: " + yearPublished);
        System.out.println("-------------------------------");
    }

    //Main method - program entry point (for now just test Book)
    public static void main(String[] args) {
        //Create first book object 
        Book book1 = new Book("The Hobbit", "J.R.R Tolkien", "978-0261103344", 2007);
        book1.displayInfo();

        //Another Book 
        Book book2 = new Book("MyBook", "Me", "WhoCares", 1997);
        book2.displayInfo();

        Book  book3 = new Book("Newbook", "ObviouslyMe", "Nobody wants to know", 9999);
        book3.displayInfo();
    }
}

