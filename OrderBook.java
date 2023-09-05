import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

    class OrderBook {
    ArrayList<String> all_rows_in_file = new ArrayList();
    ArrayList<Integer> price_bid = new ArrayList();
    ArrayList<Integer> size_bid = new ArrayList();
    ArrayList<Integer> price_ask = new ArrayList();
    ArrayList<Integer> size_ask = new ArrayList();
    File input = new File("src/input.txt");
    Scanner scan;
    PrintWriter writer;

    public void ReadFile() {
        while(this.scan.hasNextLine()) {
            this.all_rows_in_file.add(this.scan.next());
        }
    }

    public void MainLogic() {
        for(int i = 0; i < this.all_rows_in_file.size(); ++i) {
            String[] temparray = (this.all_rows_in_file.get(i)).split(",");
            if ((this.all_rows_in_file.get(i)).charAt(0) == 'u') {
                switch (temparray[3]) {
                    case "ask":
                        this.price_ask.add(Integer.valueOf(temparray[1]));
                        this.size_ask.add(Integer.valueOf(temparray[2]));
                        break;
                    case "bid":
                        this.price_bid.add(Integer.valueOf(temparray[1]));
                        this.size_bid.add(Integer.valueOf(temparray[2]));
                }
            }

            if ((this.all_rows_in_file.get(i)).charAt(0) == 'q') {
                switch (temparray[1]) {
                    case "size":
                        int j = 0;
                        for(; j < this.price_bid.size(); ++j) {
                            if (this.price_bid.get(j) == Integer.valueOf(temparray[2])) {
                                this.writer.write(this.size_bid.get(j) + "\n");
                            }
                        }
                        for(j = 0; j < this.price_ask.size(); ++j) {
                            if (this.price_ask.get(j) == Integer.valueOf(temparray[2])) {
                                this.writer.write(this.size_ask.get(j) + "\n");
                            }
                        }
                        break;
                    case "best_ask":
                            this.writer.write(this.MinValueInArray(this.price_ask) + "," + this.MinValueInArray(this.size_ask) + "\n");
                        break;
                    case "best_bid":
                            this.writer.write(this.MaxValueInArray(this.price_bid) + "," + this.MaxValueInArray(this.size_bid) + "\n");
                }
            }

            if ((this.all_rows_in_file.get(i)).charAt(0) == 'o') {
                switch (temparray[1]) {
                    case "buy":
                            this.size_ask.set(this.FindIndexMaxElement(this.price_ask), (Integer)this.size_ask.get(this.FindIndexMaxElement(this.price_ask)) - Integer.valueOf(temparray[2]));
                        break;
                    case "sell":
                            this.size_bid.set(this.FindIndexMaxElement(this.price_bid), (Integer)this.size_bid.get(this.FindIndexMaxElement(this.price_bid)) - Integer.valueOf(temparray[2]));
                }
            }
        }

        this.writer.close();
    }

    public int MaxValueInArray(ArrayList<Integer> array) {
        int max = 0;

        for(int i = 0; i < array.size(); ++i) {
            if (array.get(i) > max) {
                max = array.get(i);
            }
        }

        return max;
    }

    public int MinValueInArray(ArrayList<Integer> array) {
        int min = array.get(0);

        for(int i = 0; i < array.size(); ++i) {
            if (array.get(i) < min) {
                min = array.get(i);
            }
        }

        return min;
    }


    public int FindIndexMaxElement(ArrayList<Integer> array) {
        for(int i = 0; i < array.size(); ++i) {
            if (array.get(i) == this.MaxValueInArray(array)) {
                return i;
            }
        }
        return 0;
    }


    public OrderBook() throws FileNotFoundException {
        this.scan = new Scanner(this.input);
        this.writer = new PrintWriter("output.txt");
    }
}