public class Homework_1 {

    public static void main(String[] args) throws InterruptedException {

        int N = 1;

        Thread thread1 = new Thread(new FirstTask(N),"First");
        Thread thread2 = new Thread(new SecondTask(N),"Second");
        Thread thread3 = new Thread(new ThirdTask(N),"Third");
        Thread thread4 = new Thread(new FourthTask(N),"Fourth");
        Thread thread5 = new Thread(new FifthTask(N),"Fifth");

        long startTime = System.nanoTime();

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();

        long endTime = System.nanoTime();
        double parallelTime = (endTime - startTime) / 1_000_000.0;
        
        System.out.printf("----------------------------------------%n Параллельное выполнение: %.2f мс%n----------------------------------------%n", parallelTime);

        thread1 = new Thread(new FirstTask(N),"First");
        thread2 = new Thread(new SecondTask(N),"Second");
        thread3 = new Thread(new ThirdTask(N),"Third");
        thread4 = new Thread(new FourthTask(N),"Fourth");
        thread5 = new Thread(new FifthTask(N),"Fifth");


        startTime = System.nanoTime();

        thread1.start();
        thread1.join();

        thread2.start();
        thread2.join();

        thread3.start();
        thread3.join();

        thread4.start();
        thread4.join();

        thread5.start();
        thread5.join();

        endTime = System.nanoTime();

        parallelTime = (endTime - startTime) / 1_000_000.0;

        System.out.printf("----------------------------------------%n Последовательное выполнение: %.2f мс%n----------------------------------------%n", parallelTime);

    }

    static class FirstTask implements Runnable {
        private final int n;


        FirstTask(int n) {
            this.n = n;
            
        }

        @Override
        public void run() {
            
            String name = Thread.currentThread().getName();
            int counter = 0;
            int last = 0;
            int limit = 300_000 + n * 20_000;
            for (int i = 0; i < limit; i++){
                if (simple(i)) {
                    counter += 1;
                    if (i > last) last = i;
                };
            }
            
            
            
            System.out.println(name);
            
            System.out.println("    Количество чисел: " + counter);
            System.out.println("    Наибольшее подходящее число: " + last);

            
        }
    }

    static class SecondTask implements Runnable {
        private final int n;


        SecondTask(int n) {
            this.n = n;
            
        }
    
        @Override 
        public void run() {
            
            
            String name = Thread.currentThread().getName();
            int counter = 0;
            long sum = 0;
            int limit = 50_00_000 + n * 100_000;
            for (int i = 0; i < limit; i++){
                if ((i % (n + 2) == 0) || (i % (n + 3) != 0)) {
                    counter += 1;
                    sum += i;
                };
            }
            
            
            
            System.out.println(name);
            
            System.out.println("    Количество чисел: " + counter);
            System.out.println("    Сумма: " + sum);

            
        }
    }

    static class ThirdTask implements Runnable {
        private final int n;


        ThirdTask(int n) {
            this.n = n;
            
        }

        @Override
        public void run() {
             

            String name = Thread.currentThread().getName();
            long sum = 0;
            int limit = 10_00_000 + n * 200_000;
            for (int i = 0; i < limit; i++){
                sum += i * i;
            }
            
            
            
            System.out.println(name);
            
            System.out.println("    Сумма: " + sum);

            
        }
    }

    static class FourthTask implements Runnable {
        private final int n;


        FourthTask(int n) {
            this.n = n;
            
        }

        @Override
        public void run() {
            

            String name = Thread.currentThread().getName();
            int counter = 0;
            String str;
            char num = Character.forDigit(n % 10, 10);
            int limit = 2_000_000 + n * 50_000;
            for (int i = 0; i < limit; i++){
                str = String.valueOf(i);
                for (char ch: str.toCharArray()) {
                    if (ch == num) counter += 1;
                }
            }
            
            
            
            System.out.println(name);
            
            System.out.println("    Количество чисел: " + counter);

            
        }
    }

    static class FifthTask implements Runnable {
        private final int n;


        FifthTask(int n) {
            this.n = n;
            
        }

        @Override
        public void run() {
            

            String name = Thread.currentThread().getName();
            int counter = 0;
            int sum;
            int first = 0;
            boolean firstly = true;
            int last = 0;
            String str;
            int limit = 1_000_000 + n * 30_000;
            for (int i = 0; i < limit; i++){
                str = String.valueOf(i);
                sum = 0;
                for (char ch: str.toCharArray()) {
                    sum += ((int) ch);
                }
                if (sum % (n % 5 + 2) == 0){
                    counter += 1;
                    if (firstly) {
                        first = i;
                        firstly = false;
                    } else {
                        last = i;
                    }
                }
            }
            
            
            
            System.out.println(name);
            
            System.out.println("    Количество чисел: " + counter);
            System.out.println("    Первое подходящее число: " + first);
            System.out.println("    Последнее подходящее число: " + last);

            

        }
    }

    public static boolean simple(int num) {
        for (int i = 2; i < num; i++) {
            if (num % i == 0) return false;
        }

        return true;
    }

}