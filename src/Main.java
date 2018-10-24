import model.Tuple;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        // Initial declarations

        String line;

        List<String> splitters = new ArrayList<>();
        splitters.add(" ");

        List<Tuple<String>> codes = new ArrayList<>();
        int identifier_code = -1, constant_code = -1;



        List<Tuple<Integer>> PIF = new ArrayList<>();

        BinaryTree ST_VAR = new BinaryTree();
        BinaryTree ST_CONST = new BinaryTree();

        int round_open = 0, round_closed = 0, square_open = 0, square_closed = 0, curly_open = 0, curly_closed = 0;

        File file = new File("table.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File table.txt not found");
            System.exit(1);
        }
        while (sc.hasNextLine())
        {
            line = sc.nextLine();
            String[] s = line.split(" ");
            String name = s[0];
            int code = Integer.parseInt(s[1]);
            if (name.equals("identifier"))
            {
                identifier_code = code;
            }
            else if (name.equals("constant"))
            {
                constant_code = code;
            }
            else
            { codes.add(new Tuple<>(name,code)); }

        }
        if (identifier_code == -1 || constant_code == -1)
        {
            System.out.println("Invalid configuration file !");
            System.exit(2);
        }
        sc.close();

        // Main algorithm

        int line_counter = 1;
        file = new File("program.txt");
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File program.c not found");
            System.exit(1);
        }
        while (sc.hasNextLine())
        {
            try
            {
                line = sc.nextLine();
                List<String> tokens = Arrays.stream(line.split("(\\+|-|\\*|/|:=|<|>|<=|>=|=|!=|and|or|not|\\(|\\)|\\[|\\]|\\{|\\}|:|;|,| )")).filter(item -> !"".equals(item)).collect(Collectors.toList());

                for (String s : tokens)
                {
                    boolean flag = false;

                    //Console.WriteLine(s);
                    s.replace(" ", "");

                    if (codes.contains(s))
                    {
                        System.out.println("SPECIAL: " + s);

                        if (s.equals("("))
                            round_open++;
                        else if (s.equals( ")"))
                            round_closed++;
                        else if (s.equals( "["))
                            square_open++;
                        else if (s.equals( "]"))
                            square_closed++;
                        else if (s.equals("{"))
                            curly_open++;
                        else if (s.equals("}"))
                            curly_closed++;

                        if(round_closed>round_open || square_closed > square_open || curly_closed > curly_open)
                        {
                            System.out.println("Paranthesis error @ line " + line_counter);
                            System.exit(3);
                        }

                        PIF.add(new Tuple<>(codes.stream().filter(item -> item.x.equals(s)).findFirst().orElse(null).y, -1));
                    }
                    else if(s.equals("true") || s.equals("false"))
                    {
                        System.out.println("BOOLEAN: " + s);
                        flag = true;
                    }
                    else if (s.matches("^'[a-zA-Z0-9]?'$"))
                    {
                        System.out.println("CHAR: " + s);
                        flag = true;
                    }
                    else if (s.matches( "^\"[a-zA-Z0-9]*\"$"))
                    {
                        System.out.println("STRING: " + s);
                        flag = true;
                    }
                    else if (s.matches( "^[0-9]+.[0-9]+$"))
                    {
                        System.out.println("REAL: " + s);
                        flag = true;
                    }
                    else if (s.matches( "^[0-9]+$"))
                    {
                        System.out.println("NUMBER: " + s);
                        flag = true;
                    }
                    else if(s.matches( "^[a-zA-Z][a-zA-Z0-9]*$"))
                    {
                        System.out.println("IDENTIFIER: " + s);

                        if (s.length() > 8) {
                            System.out.println("Identifiers are not allowed to be longer than 8 characters! @ Line " + line_counter);
                            System.exit(3);
                        }

                        Tuple<String> identifier = new Tuple<>(s);
                        ST_VAR.add(identifier);
                        PIF.add(new Tuple<>(identifier_code, identifier.y));
                    }
                    else
                    {
                        System.out.println("Lexical error, unknown token @ line : " + line_counter);
                        System.exit(3);
                    }

                    if(flag)
                    {
                        Tuple<String> constant = new Tuple<>(s);
                        ST_CONST.add(constant);
                        PIF.add(new Tuple<>(constant_code, constant.y));
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println("Fatal error @ Line " + line_counter + " " + e.getMessage());
            }
            line_counter++;
        }
        sc.close();

        if (round_closed != round_open || square_closed != square_open || curly_closed != curly_open)
        {
            System.out.println("Not all opened parantheses have been closed!");
            System.exit(3);
        }

        System.out.println("ST_VAR");
        for (Tuple t :ST_VAR.postorder())
            System.out.println(t.x + " " + t.y);
        System.out.println();

        System.out.println("ST_CONST");
        for (Tuple t :ST_CONST.postorder())
            System.out.println(t.x + " " + t.y);
        System.out.println();

        System.out.println("PIF");
        for (Tuple t : PIF)
            System.out.println(t.x + " " + t.y);
    }
}
