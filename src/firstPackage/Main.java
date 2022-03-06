package firstPackage;

import java.util.*;

public class Main {
    // lista główna
    // używamy przez cały czas działania programu

    static List<String> list = new ArrayList<>();

    // wyświetlanie listy podanej jako parametr

    public static void printList(List<String> l) {
        System.out.print("\nLIST: ");
        for(String s : l)
            System.out.print(s + "; ");
        System.out.println("");
    }

    // metoda zwracająca nową listę z odwrocnymi wyrazami z listy głównej
    // korzystając z klasy StringBuilder możemy użyć metody reverse do odwrócenia wyrażeń na liście głównej
    // najpierw tworzymy objekt typu StringBuilder
    //      metoda 'append(String)' z klasy StringBuilder: dodaje podany ciąg do strB
    // dalej tworzymy obiekt typu String i przypisujemy mu odwracaną wartość obiektu StringBuilder (metoda 'reverse()')
    //      metoda 'toString()' z klasy StringBuilder: zwraca ciąg znaków
    // dodajemy zmienną typu String do nowej listy

    public static List<String> reverseStringList() {
        List<String> stringList = new ArrayList<>();
        for (String s : list) {
            StringBuilder strB = new StringBuilder();
            strB.append(s);
            String strEnd = strB.reverse().toString();
            stringList.add(strEnd);
        }
        return stringList;
    }

    // metoda wypisująca ciągi w kolejności rosnącej
    //      korzystając z interfejsów Comparator i List możemy skorzystać z kilku przydatnych metod
    //      metoda 'sort' z interfejsu List: sortuje listę w kolejności określonej przez podany Comparator
    //      metoda 'comparing' z interfejsu Comparator: przyjmuje funkcję 'length' która wyodrębnia klucz
    //              sortowania i zwraca Comparator<String> który porównuje z kluczem sortowania
    // wraz z informacją jako:
    // 1. który ciąg był początkowo wprowadzony
    //      deklarujemy zmienną firstWord, która oznacza pierwszy element na liście,
    //      za pomocą której od razu ustalamy, które wyrażenie zostało przesłane przez użytkownika jako pierwsze
    //      metoda 'get(i)' z interfejsu List zwraca element na pozycji i
    // 2. liczby wyrazów z których jest złożony
    //      funkcja howMuchWords(String) - opisano poniżej
    // 3. ile razy każdy znak wystąpił w danym ciągu
    //      funkcja howManyOneChar(String) - opisano poniżej

    public static void sortStringList() throws Exception {
        String firstWord = list.get(0);
        list.sort(Comparator.comparing(String::length));
        for(int i=0; i<list.size(); i++) {
            String element = list.get(i);
            System.out.print("\n" + (i+1) + ". " + element);
            if(element.equals(firstWord)) System.out.print(" - this element was introduced first.");
            System.out.print("\n\tCount of words in element: " + howMuchWords(element));
            howManyOneChar(element);
        }
    }

    // metoda zwraca liczbę powtórzeń słowa w wyrażeniu
    // tworzymy zmienną typu int 'count', którą zwracamy na końcu
    // również została stworzona tablica znaków 'chars'
    //      metoda 'toCharArray()' z klasy String: konwertuje ciąg w tablicę znakową
    // sprawdzamy całą tablicę
    // i jeśli element nie jest spacją, jest literą i:
    //      nie jest już wyrazem pierwszym
    // lub
    //      jest wyrazem pierwszym
    // to zwiększamy licznik count

    public static int howMuchWords(String str) {
        int count = 0;
        char[] chars = str.toCharArray();
        for(int i=0; i<=(chars.length-1); i++)
            if( (i>0 && chars[i-1] == ' ' || i==0) && chars[i] != ' ' && Character.isLetter(chars[i]))
                count++;
        return count;
    }

    // metoda wypisuje ile razy znak występuje w wyrażeniu
    // tworzymy kolekcję Map (klucz: element - wartość: liczba wystąpień) z implementacją HashMap
    // również została stworzona tablicę znaków 'chars'
    // sprawdzamy całą tablicę
    // jeśli element z tablicy nie jest spacją:
    //      wtedy sprawdzamy za pomocą metody 'containsKey(key)' z interfejsu Map czy jest właśnie w tej mapie klucz
    //          gdy zwraca true - do już istniejącej wartości dodajemy 1
    //          w innym przypadku wprowadzamy 1
    // na końcu wypisujemy mapę (klucz - wartość)

    public static void howManyOneChar(String str) throws Exception {
        Map<Character, Integer> charMap = new HashMap<>();
        char[] chars = str.toCharArray();
        for(char c : chars)
            if(c != ' ') {
                if(charMap.containsKey(c))
                    charMap.put(c, charMap.get(c) + 1);
                else
                    charMap.put(c, 1);
            }
        for(Map.Entry e : charMap.entrySet())
            System.out.print("\n\t" + e.getKey() + " - " + e.getValue());
    }

    // metoda odwraca kolejność wyświetlania ciągów
    //      korzystając z interfejsów Comparator i List możemy skorzystać z kilku przydatnych metod
    //      metoda 'sort' z interfejsu List: sortuje listę w kolejności określonej przez podany Comparator (długość wyrazu)
    //      metoda 'comparing' z interfejsu Comparator: przyjmuje funkcję 'length' która wyodrębnia klucz
    //              sortowania i zwraca Comparator który porównuje z kluczem sortowania
    //      metoda 'reversed()' z interfejsu Comparator: zwraca Comparator który narzuca odwrotną kolejność naszej listy

    public static void orderDisplayString() {
        list.sort(Comparator.comparing(String::length).reversed());
    }


    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        // zadanie 1: pozwala użytkownikowi na wprowadzenie dowolnej liczby ciągów znakowych lub zdań
        // ciągi są dodawane do listy
        // dopóki nie wpiszemy „stop”, kontynuujemy wpisywanie ciągów
        // jeśli ciąg zostanie wprowadzony niepoprawnie, program poprosi o podanie poprawnych danych
        // również sprawdzana jest długość listy: w przypadku listy pustej program zgłasza wyjątek i kończy działanie

        System.out.println("ENTER A CHARACTER, WORD OR SENTENCE [type 'stop' to stop]: ");
        while(true) {
            String str = scan.nextLine();
            if(str.toLowerCase().equals("stop"))
                break;
            if(str == null ||
                    str.trim().equals("") || str.trim().equals(" ") ||
                    str.isEmpty() || str.trim().equals("\n"))
                System.out.println("It's possible that your string is incorrect. Please check.");
            else {
                list.add(str);
                System.out.println("OK, next.");
            }
        }
        if(list.size() == 0)
            throw new IndexOutOfBoundsException("You have not entered any character.");

        printList(list);

        // zadanie 2: odwraca kolejność wyrazów w każdym z ciągów (od ostatniego so pierwszego wyrazu)
        // tworzymy nową listę do wypełnienia nowymi zmienionymi wyrazami
        // opis metody reverseStringList() znajduje się powyżej

        System.out.print("\nYOUR WORDS IN A DIFFERENT ORDER: ");
        List<String> list2 = reverseStringList();
        printList(list2);


        // zadanie 3: wyprowadza przetworzone ciągi w kolejności od najkrótszego do najdłuższego z pewnymi informacjami
        // opis metody sortStringList() znajduje się powyżej

        sortStringList();
        printList(list);

        // zadanie 4: umożliwia użytkownikowi zmianę kolejności wyświetlania ciągów (od najdłuższego do najkrótszego)
        // program prosi użytkownika o odpowiedź 'y' lub 'n' (w przypadku innej odpowiedzi rzuca wyjątek) - czy wyświetlać odwróconą listę
        // jeśli 'y':
        //      opis metody 'orderDisplayString()' znajduje się powyżej; wypisujemy listę
        // jeśli 'n':
        //      program rzuca wyjątek Exception

        System.out.println("\nDO YOU WANT TO DISPLAY YOUR STRINGS IN ANOTHER ORDER? ['y' - yes, 'n' - no]");
        String answer = scan.nextLine();
        if(answer.equals("y")) {
            orderDisplayString();
            printList(list);
        } else if(!(answer.equals("n")) && (!answer.equals("y")))
            throw new Exception("You have entered incorrect answer.");

        System.out.println("\nTHANK YOU FOR USING THIS APP.");
    }


}
