import java.util.LinkedList;

public class Cache {
    public static LinkedList<Integer> addrList = new LinkedList<>();
    public static LinkedList<Integer> contentList = new LinkedList<>();
    public static int max = 16;

    public static int read(int addr) {
        int pos = addrList.indexOf(addr);
        if (pos == -1) {
            return -1;
        }
        int content = contentList.get(pos);

        addrList.remove(pos);
        contentList.remove(pos);

        addrList.addLast(addr);
        contentList.addLast(content);

        return content;
    }

    public static void write(int addr, int content) {
        if (addrList.contains(addr)) {
            int pos = addrList.indexOf(addr);
            int prevContent = contentList.get(pos);

            if (prevContent != content) {
                addrList.remove(pos);
                contentList.remove(pos);

                addrList.addLast(addr);
                contentList.addLast(content);
            }

            return;
        }

        if (addrList.size() >= max) {
            Memory.ProcessorMemory[addrList.removeFirst()] = contentList.removeFirst();
            SystemControl.simulateDelay();
        }

        addrList.addLast(addr);
        contentList.addLast(content);
    }


    public static void flush() {
        for (int i = 0; i < addrList.size(); i++) {
            Memory.ProcessorMemory[addrList.get(i)] = contentList.get(i);
        }
        addrList = new LinkedList<>();
        contentList = new LinkedList<>();
    }




}
