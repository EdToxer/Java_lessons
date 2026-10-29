public class Homework_3 {

    public enum Sign {
        PLUS, MINUS
    }

    public static String step = "-----";

    public static class BankAccount {
        private static int balance = 1_000_000;

        public static void operation_not_sync(Sign sign, int amount){
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero.");
            }
            if (sign == Sign.PLUS) {
                deposit(amount);
            } else if (sign == Sign.MINUS) {
                withdraw(amount);
            }
        }

        public static synchronized void operation_sync(Sign sign, int amount){
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero.");
            }
            if (sign == Sign.PLUS) {
                deposit(amount);
            } else if (sign == Sign.MINUS) {
                withdraw(amount);
            }
        }

        private static void deposit(int amount) {
            if (balance + amount > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("Balance must not be greater than limit.");
            } else balance += amount;
        }

        private static void withdraw(int amount) {
            if (amount > balance) {
                throw new IllegalArgumentException("Balance must be greater than amount withdrawing.");
            } else balance -= amount;
        }

        public static void return_back() {
            balance = 1_000_000;
        }

        public static String get_balance() {
            return "Actual balance: " + balance;
        }
    }

    public static class Operator implements Runnable {
        private int iterations;
        private int N;
        private boolean sync;

        Operator(int iterations, int N, boolean sync){
            this.iterations = iterations;
            this.N = N;
            this.sync = sync;
        }

        @Override
        public void run(){
            Sign cur_sign = Sign.PLUS;
            int value = N + 10;
    
            for (int i = 0; i < iterations; i++) {
                if (sync) {
                    BankAccount.operation_sync(cur_sign, value);
                } else {
                    BankAccount.operation_not_sync(cur_sign, value);
                }

                value = (cur_sign == Sign.PLUS) ? N + 5 : N + 10;
                cur_sign = (cur_sign == Sign.PLUS) ? Sign.MINUS : Sign.PLUS;
            
            }   
        }
    }

    public static class Manager {
        private int K;
        private int N;
        private int iterations;
        private Thread[] threads;
        private boolean sync;
        private long startTime;
        private long endTime;
        private int expectedBalanceWithout;
        private String name;

        Manager(String name, int N, int balance, boolean sync) {
            this.name = name;
            this.K = 3 + N % 4;
            this.iterations = 100_000 + N * 5_000;
            this.threads = new Thread[K];
            this.sync = sync;
            this.expectedBalanceWithout = 1_000_000 + ((((N + 10) - (N + 5)) * iterations/2) * K);
        }

        private void start_time() {
            startTime = System.currentTimeMillis();
        }

        private void end_time() {
            endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.printf("time: %d ms\n", duration);
        }

        private void prepare() {
            for (int i = 0; i < K; i++){
                threads[i] = new Thread(new Operator(iterations, N, sync));
            }
        }

        private void start() {
            for (int i = 0; i < K; i++){
                threads[i].start();
            }
        }

        private void join() throws InterruptedException {
            for (int i = 0; i < K; i++){
                threads[i].join();
            }
        }

        private void result() {
            System.out.println(BankAccount.get_balance());
            BankAccount.return_back();
        }

        private void template() throws InterruptedException {
            this.start_time();
            this.prepare();
            this.start();
            this.join();
            this.end_time();
            this.result();
            
        }

        public void execute() throws InterruptedException {
            System.out.printf(step);
            System.out.println();
            System.out.print(this.name);
            if (sync) {
                System.out.printf("-sync, ");
                this.template();
                System.out.printf("Expect balance: %d%n", expectedBalanceWithout);
            } else {
                System.out.printf("-unsync, ");
                this.template();
                System.out.printf("Expect balance: %d%n", expectedBalanceWithout);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        
        int N = 1;
        int K = 3 + N % 4;
        int iterations = 100_000 + N * 5_000;

        System.out.printf("Вариант: %d%n", N);
        System.out.printf("Threads: %d%n", K);
        System.out.printf("Iterations: %d%n", iterations);

        Manager sbernank = new Manager("SberBank" ,1, 1_000_00, false);
        sbernank.execute();

        Manager vtbbank = new Manager("VtbBank", 1, 1_000_00, false);
        vtbbank.execute();

        Manager alfanank = new Manager("AlfaBank", 1, 1_000_00, false);
        alfanank.execute();

        Manager tbank = new Manager("TBank", 1, 1_000_00, true);
        tbank.execute();

    }   
}
