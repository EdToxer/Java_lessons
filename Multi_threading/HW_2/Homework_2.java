public class Homework_2 {

    static public int sumDigits(long num) {
        String str = String.valueOf(num);
        int sum = 0;
        for (char ch: str.toCharArray()) {
            sum += ((int) ch);
        }

        return sum;
    }
    
    
    static class ArrayTask implements Runnable {

        private int[] array;
        private int start;
        private int end;

        private long sum = 0;
        private long sum_A = 0;
        private int count_B = 0;
        private int count_A = 0;
        private int max = 0;

        private  int A;
        private  int B;
        

        public ArrayTask(int N, int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.A = N + 3;
            this.B = N % 4 + 2;
        }

        @Override
        public void run() {

            for (int i = start; i < end; i++) {

                // Частичная сумма
                sum += array[i];

                // Количество чисел, делящихся на 7
                if (array[i] % A == 0) {
                    count_A ++;
                    sum_A += i;
                }

                // Количество чисел, делящихся на 7
                if (array[i] % B == 0) {
                    count_B ++;
                }

                // Максимальное число, делящееся на 13
                if (array[i] % A == 0 && sumDigits(i) % B == 0) {
                    max = array[i];
                }
            }
        }

        public long get1() {
            return sum;
        }

        public int get2() {
            return count_A;
        }

        public long get3() {
            return sum_A;
        }

        public int get4() {
            return count_B;
        }

        public int get5() {
            return max;
        }
    }

    static class RangeTask {
        private int N;
        private int K;
        private int length;
        private int[] array;
        private int part;
        private int last;
        private long[] results;
        private long startTime;
        private long endTime; 
        private double parallelTime;
        private String name;

        ArrayTask[] scheduler_tasks;
        Thread[] scheduler;


        RangeTask(String name, int N){
            this.N = N;
            this.K = 2 + N % 5;
            this.length = 10_000_000 + N * 500_000;
            this.array = new int[length];
            this.scheduler_tasks = new ArrayTask[K];
            this.scheduler = new Thread[K];
            this.part = length / K;
            this.last = 0;
            this.results =  new long[5];
            this.name = name;
        }

        private void run_parallel() throws InterruptedException {
            for (int i = 0; i < K; i++){
                scheduler[i].start();
            }
            for (int i = 0; i < K; i++){
                scheduler[i].join();
            }
        }

        private void run_sequential() throws InterruptedException {
            for (int i = 0; i < K; i++){
                scheduler[i].start();
                scheduler[i].join();
            }
        }

        public void start(boolean parallel) throws InterruptedException {
            for (int i = 0; i < this.array.length; i++) {
                this.array[i] = i + 1;
            }

            for (int i = 0; i < K - 1; i++){
            scheduler_tasks[i] = new ArrayTask(N, array, part * i, part * i + 1);
            last = i;
            }

            scheduler_tasks[K-1] = new ArrayTask(N, array, part * last, part * K);

            for (int i = 0; i < K; i++){
                scheduler[i] = new Thread(scheduler_tasks[i]);
            }

            startTime = System.nanoTime();

            if (parallel) {
                run_parallel();
            } else {
                run_sequential();
            }

            endTime = System.nanoTime();
            parallelTime = (endTime - startTime) / 1_000_000.0;

            for (int i = 0; i < K; i++) {
                var task = scheduler_tasks[i];
                
                results[0] += task.get1();
                results[1] += task.get2();
                results[2] += task.get3();
                results[3] += task.get4();
                results[4] += task.get5();
                
            }
        }

        @Override
        public String toString() {
            System.out.printf("%.2f мс%n", parallelTime);
            return String.format(
                this.name + "%n" +
                "Сумма всех чисел: %d%n" +
                "Количество А: %d%n" +
                "Сумма A: %d%n" +
                "Количество B: %d%n" +
                "Максимальное число: %d",
                this.results[0], this.results[1], this.results[2], this.results[3], this.results[4]
            );
        }
    }

    public static void main(String[] args) throws InterruptedException {

        RangeTask thread_parallel = new RangeTask("Parallel", 1);

        RangeTask thread_sequential = new RangeTask("Sequential", 1);

        thread_parallel.start(true);

        thread_sequential.start(false);

        System.out.println(thread_parallel);

        System.out.println();

        System.out.println(thread_sequential);
    }
}