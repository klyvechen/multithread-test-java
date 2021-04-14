package rm.project;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class NotifyingThread extends Thread {
    private final Set<ThreadCompleteListener> listeners =
            new CopyOnWriteArraySet<ThreadCompleteListener>();

    public final void addListener(final ThreadCompleteListener listener) {
        listeners.add(listener);
    }

    public final void removeListener(final ThreadCompleteListener listener) {
        listeners.remove(listener);
    }

    private final void notifyListeners() {
        for (ThreadCompleteListener listener : listeners) {
            listener.notifyOfThreadComplete(this);
        }
    }

    private final Runnable runnable;

    public String threadName;

    public final long startTime;

    public long endTime;

    public NotifyingThread(Runnable runnable) {
        this.runnable = runnable;
        this.startTime = new Date().getTime();
    }

    public NotifyingThread(Runnable runnable, long startTime) {
        this.runnable = runnable;
        this.startTime = startTime;
    }

    @Override
    public final void run() {
        threadName = Thread.currentThread().getName();
        try {
            runnable.run();
        } finally {
            notifyListeners();
        }
    }

}
