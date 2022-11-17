import java.util.LinkedList;

public class Cache {

    private final SystemControl systemControl;
    public LinkedList<Integer> addrList = new LinkedList<>();
    public LinkedList<Integer> contentList = new LinkedList<>();
    public int max = 16;

    public Cache(SystemControl systemControl, int max) {
        this.systemControl = systemControl;
        this.max = max;
    }

    public int read(int addr) {
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

    public void write(int addr, int content) {
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
            systemControl.memory.ProcessorMemory[addrList.removeFirst()] = contentList.removeFirst();
            systemControl.simulateDelay();
        }

        addrList.addLast(addr);
        contentList.addLast(content);
    }


    public void flush() {
        for (int i = 0; i < addrList.size(); i++) {
            systemControl.memory.ProcessorMemory[addrList.get(i)] = contentList.get(i);
        }
        addrList = new LinkedList<>();
        contentList = new LinkedList<>();
    }




}
