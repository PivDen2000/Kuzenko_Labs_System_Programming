//variant 11
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class Lab3_System_Programming {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        scanner.close();

        File file = new File(filename);
        scanner = new Scanner(file);

        ArrayList<String> Output = new ArrayList<String>();
        List<String> Reserved = Arrays.asList("return","with","as","class","for","in","if","def","else","elif","and","or","not",
        "True","False");
       
        String line = "";
        String word = "";
        String number = "";
        String operator = "";
        boolean quote = false;
        boolean dquote = false;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    
                    if (line.charAt(j) == ' ') {
                        if (quote || dquote){
                            word += line.charAt(j);
                        } else {
                            push(Output, Reserved, word, number, quote, dquote);
                            pushOp(Output, operator, quote, dquote); 
                            word = "";
                            number = "";
                            operator = "";
                        }
                    }

                    if (line.charAt(j) == '#') {
                        if(!quote && !dquote){
                        push(Output, Reserved, word, number, quote, dquote);
                        pushOp(Output, operator, quote, dquote); 
                        word = "";
                        number = "";
                        operator = "";
                        Output.add(line.substring(line.indexOf('#')) + "\t-\tComment");
                        j = line.length();
                        break;
                        } else {
                            word += line.charAt(j);
                        } 
                    }

                    if ((""+line.charAt(j)).matches("[a-zA-Z_]")) {
                        word += line.charAt(j);
                        pushOp(Output, operator, quote, dquote);
                        operator = "";
                    }

                    if ((""+line.charAt(j)).matches("[0-9]")) {
                        if(!quote && !dquote){
                        if (word.isEmpty()) {
                            number += line.charAt(j);
                            pushOp(Output, operator, quote, dquote);
                            operator = "";
                        } else {
                            word += line.charAt(j);
                        }} else {
                            word += line.charAt(j);
                        }
                    }

                    if ((""+line.charAt(j)).matches("[,;:?]")
                    || line.charAt(j) == '['
                    || line.charAt(j) == ']'
                    || line.charAt(j) == '('
                    || line.charAt(j) == ')'
                    || line.charAt(j) == '{'
                    || line.charAt(j) == '}') {
                        if(!quote && !dquote){
                        push(Output, Reserved, word, number, quote, dquote);
                        pushOp(Output, operator, quote, dquote);
                        word = "";
                        number = "";
                        operator = "";
                        Output.add(line.charAt(j) + "\t-\tOperator");
                        } else {
                            word += line.charAt(j);
                        }
                    }

                    if(line.charAt(j) == '*'
                    || line.charAt(j) == '+'
                    || line.charAt(j) == '-'
                    || line.charAt(j) == '/'
                    || line.charAt(j) == '%'
                    || line.charAt(j) == '='
                    || line.charAt(j) == '<'
                    || line.charAt(j) == '>'){
                        if(!quote && !dquote){
                        operator += line.charAt(j);
                        push(Output, Reserved, word, number, quote, dquote);
                        word = "";
                        number = "";
                        } else {
                            word += line.charAt(j);
                        }
                    }

                    if(line.charAt(j) == '.'){
                        if(!quote && !dquote){
                        if ((""+line.charAt(j-1)).matches("[0-9]")) {
                            number += line.charAt(j);
                        }
                        if ((""+line.charAt(j-1)).matches("[a-zA-Z_]")) {
                            push(Output, Reserved, word, number, quote, dquote);
                            word = "";
                            Output.add(line.charAt(j) + "\t-\tOperator");
                        }
                        } else {
                            word += line.charAt(j);
                        }
                        }

                    if(line.charAt(j) == '\''){
                        if (!quote){
                        if (!dquote) {
                        push(Output, Reserved, word, number, quote, dquote);
                        word = "";
                        number = "";
                        word += line.charAt(j);
                        } 
                    } else {
                            word += line.charAt(j);
                            push(Output, Reserved, word, number, quote, dquote);
                            word = "";
                        }
                    if(line.charAt(j-1) != '\\'){
                        quote = !quote;
                    }
                    }

                    if(line.charAt(j) == '\"'){
                        if (!dquote){
                        if (!quote) {
                        push(Output, Reserved, word, number, quote, dquote);
                        word = "";
                        number = "";
                        word += line.charAt(j);
                        } 
                    } else {
                            word += line.charAt(j);
                            push(Output, Reserved, word, number, quote, dquote);
                            word = "";
                        }
                    if(line.charAt(j-1) != '\\'){
                        dquote = !dquote;
                    }
                    }

                    if ((""+line.charAt(j)).matches("[^a-zA-Z_\'\"0-9%<>=.,;:# ]")
                    && line.charAt(j) != '*'
                    && line.charAt(j) != '+'
                    && line.charAt(j) != '-'
                    && line.charAt(j) != '/'
                    && line.charAt(j) != ']'
                    && line.charAt(j) != '('
                    && line.charAt(j) != ')'
                    && line.charAt(j) != '['
                    && line.charAt(j) != '{'
                    && line.charAt(j) != '}') {
                        push(Output, Reserved, word, number, quote, dquote);
                        pushOp(Output, operator, quote, dquote);
                        word = "";
                        number = "";
                        operator = "";
                        Output.add(line.charAt(j) + "\t-\tUnknown");
                    }

                }
                push(Output, Reserved, word, number, quote, dquote);
                pushOp(Output, operator, quote, dquote);
                word = "";
                number = "";
                operator = "";
        }
        scanner.close();

        try(FileWriter writer = new FileWriter("Output.txt", false))
        {
            for (int i = 0; i < Output.size(); i++) {
                if(!Output.get(i).contains("\t-\tComment")){
                writer.write(Output.get(i));
                if(i != Output.size()-1) {writer.append('\n');}}
            }
            writer.flush();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        } 
    }

    public static void push(ArrayList<String> Output, List<String> Reserved, String word, String number, boolean quote, boolean dquote) {
       
        if(!word.isEmpty()){
            if (word.equals("import") && !quote && !dquote){
                Output.add(word + "\t-\tDirective");
            } else
            if (Reserved.contains(word) && !quote && !dquote) {
                Output.add(word + "\t-\tReserved");
            } else {
                Output.add(word + "\t-\tString");
            }
        }
       
        if(!number.isEmpty()){
            Output.add(number + "\t-\tNumber");
        }
    }
    public static void pushOp(ArrayList<String> Output, String operator, boolean quote, boolean dquote) {
        if(!operator.isEmpty() && !quote && !dquote){
            Output.add(operator + "\t-\tOperator");
        }   
    }

}