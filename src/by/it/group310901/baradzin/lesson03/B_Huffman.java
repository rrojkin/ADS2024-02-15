package by.it.group310901.baradzin.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Восстановите строку по её коду и беспрефиксному коду символов
 *
 * В первой строке входного файла заданы два целых числа kk и ll через пробел — количество различных букв,
 * встречающихся в строке, и размер получившейся закодированной строки, соответственно.
 *
 * В следующих kk строках записаны коды букв в формате "letter: code". Ни один код не является префиксом другого.
 * Буквы могут быть перечислены в любом порядке. В качестве букв могут встречаться лишь строчные буквы латинского
 * алфавита; каждая из этих букв встречается в строке хотя бы один раз. Наконец, в последней строке записана
 * закодированная строка. Исходная строка и коды всех букв непустые. Заданный код таков, что закодированная строка
 * имеет минимальный возможный размер.
 *
 * Sample Input 1:
 * 1 1
 * a: 0
 * 0
 * Sample Output 1:
 * a
 *
 * Sample Input 2:
 * 4 14
 * a: 0
 * b: 10
 * c: 110
 * d: 111
 * 01001100100111
 * Sample Output 2:
 * abacabad
 */

public class B_Huffman {

    public static void main(String[] args) throws FileNotFoundException {
        var root = System.getProperty("user.dir") + "/src/";
        var f = new File(root + "by/it/group310901/baradzin/lesson03/encodeHuffman.txt");
        var instance = new B_Huffman();
        var result = instance.decode(f);
        System.out.println(result);
    }

    String decode(File file) throws FileNotFoundException {
        var scanner = new Scanner(file);
        var count = scanner.nextInt();
        scanner.nextLine();

        var head = new NodeHead();
        for (var i = 0; i < count; i++) {
            var line = scanner.nextLine();
            head.push(line.substring(3), line.charAt(0));
        }
        return head.parse(scanner.nextLine(), head);
    }

    static class NodeNum {
        NodeNum Node0 = null;
        NodeNum Node1 = null;

        void push(String code, Character symbol) {
            var is1 = code.startsWith("1");
            if (code.length() == 1) {
                if (is1)
                    Node1 = new NodeChar(symbol);
                else
                    Node0 = new NodeChar(symbol);
            } else if (is1) {
                if (Node1 == null)
                    Node1 = new NodeNum();
                Node1.push(code.substring(1), symbol);
            } else {
                if (Node0 == null)
                    Node0 = new NodeNum();
                Node0.push(code.substring(1), symbol);
            }
        }

        String parse(String encoded, NodeHead head) {
            return (encoded.startsWith("1") ? Node1 : Node0).parse(encoded.substring(1), head);
        }
    }

    static class NodeHead extends NodeNum {
        @Override
        String parse(String encoded, NodeHead head) {
            return (encoded.startsWith("1") ? Node1 : Node0).parse(encoded.substring(1), this);
        }
    }

    static class NodeChar extends NodeNum {
        Character symbol;

        NodeChar(Character symbol) {
            this.symbol = symbol;
        }

        @Override
        String parse(String encoded, NodeHead head) {
            return symbol + (encoded.isEmpty() ? "" : head.parse(encoded, head));
        }
    }
}
