package rm.project.multi_thread_p2;

public class IntConsumer {

    private final int type;

    public IntConsumer(int type) {
        this.type = type;
    }

    public void accept(int i) {
        if (this.type == 0) {
            if (i != 0) {
                System.out.print("Error: type is 0 but input is not 0.");
                System.exit(-1);
            }
        } else {
            if (i % this.type != 0) {
                System.out.print("Error: input is not belong to the type.");
                System.out.print(" input " + i);
                System.out.print(",type " + type);
                System.exit(-1);
            }
        }
        System.out.print(i);
    }
}
