import java.util.LinkedList;

//Defining the class and implementing as linked list
public class Cache {
    private final SystemCore systemCore;
    int CacheSize;
    
    LinkedList<Integer> CacheAddress = new LinkedList<>();
    LinkedList<Integer> CacheContent = new LinkedList<>();

    public Cache(SystemCore systemCore, int CacheSize) {
        this.systemCore = systemCore;
        this.CacheSize = CacheSize;
    }

    //Reading the Addressess & content
    public int read(int Address) {
        int pos = CacheAddress.indexOf(Address);
        if (pos == -1) {
            systemCore.frame.set_cache_bg(-1);
            return -1;
        }
        int content = CacheContent.get(pos);

        CacheAddress.remove(pos);
        CacheContent.remove(pos);

        CacheAddress.addLast(Address);
        CacheContent.addLast(content);

        systemCore.frame.update_cache_hit_miss(pos);

        return content;
    }

    //Writing the Address and content
    public void write(int Address, int content) {
        if (CacheAddress.contains(Address)) {
            int pos = CacheAddress.indexOf(Address);
            int prevContent = CacheContent.get(pos);

            if (prevContent != content) {
                CacheAddress.remove(pos);
                CacheContent.remove(pos);

                CacheAddress.addLast(Address);
                CacheContent.addLast(content);
            }

            systemCore.frame.update_cache_hit_miss(pos);

            return;
        }

        if (CacheAddress.size() >= this.CacheSize) {
            int x = CacheAddress.removeFirst();
            int y = CacheContent.removeFirst();
            systemCore.memory.ProcessorMemory[x] = y;
            systemCore.frame.update_memory_single(y, x);
        }

        CacheAddress.addLast(Address);
        CacheContent.addLast(content);

        systemCore.frame.set_cache_bg(-1);
    }

    //Adding Address and content to the linked list
    public void flush() {
        for (int i = 0; i < CacheAddress.size(); i++) {
            systemCore.memory.ProcessorMemory[CacheAddress.get(i)] = CacheContent.get(i);
        }
        CacheAddress = new LinkedList<>();
        CacheContent = new LinkedList<>();
    }
}
