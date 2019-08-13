package cn.gyyx.bts.core.ctrl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import cn.gyyx.bts.core.Timer;
import cn.gyyx.bts.core.TimerCallBackFunc;

public class ZooCtrl {

    private static final Logger logger = LoggerFactory.getLogger(ZooCtrl.class);

    private CuratorFramework curator = null;

    private InterProcessMutex lock = null;

    private final TimerCtrl timerMrg;

    private final String CONNECT_STRING;
    
    private final ZooPathCtrl zooPathMrg;
    
    
    @Inject
    public ZooCtrl(ZooPathCtrl zooPathMrg,TimerCtrl timerMrg) {
        this.timerMrg = timerMrg;
        this.zooPathMrg=zooPathMrg;
        CONNECT_STRING="127.0.0.1";
    }

    /**
     * 获取一个可用的zookeeper连接,如果不存在则创建一个,采用懒汉式方式管理器
     *
     * @return
     */
    public CuratorFramework getCurator() throws IOException {
        if (null == curator) {
            curator = constructCurator();
            timerMrg.addTimer(new TimerCloseZooMrgCurator(this::shutdownCurator));
        }
        return curator;
    }

    private final class TimerCloseZooMrgCurator extends Timer {

        protected TimerCloseZooMrgCurator(TimerCallBackFunc callBackFunc) {
            super(10000L, 1, callBackFunc);
        }

    }

    /**
     * 关闭一个curator
     */
    private void shutdownCurator(Timer timer) {
        assert curator != null;
        CloseableUtils.closeQuietly(curator);
        curator = null;
    }

    /**
     * 创建一个zookeeper实例
     *
     * @return
     * @throws IOException
     */
    public CuratorFramework constructCurator() throws IOException {
        CuratorFramework curator = CuratorFrameworkFactory
                .builder()
                .namespace("bts")
                .connectString(CONNECT_STRING)
                .connectionTimeoutMs(50000)
                .sessionTimeoutMs(10000)
                .retryPolicy(
                        new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .build();
        curator.start();
        while(true){
            if(curator.getState()!= CuratorFrameworkState.STARTED){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            break;
        }
        return curator;
    }

    public void lock() throws Exception {

        CuratorFramework curator = getCurator();

        lock = new InterProcessMutex(curator, zooPathMrg.getGlobalLockPath());

        lock.acquire(365, TimeUnit.DAYS);

    }

    public void unLock() throws Exception {
        lock.release();
        lock = null;
    }

    public void setData(String path, byte[] data) throws Exception {

        if (!checkExist(path)) {

            throw new RuntimeException(path);

        }
        getCurator().setData().forPath(path, data);
    }

    public byte[] getData(String path) throws Exception {
        byte[] binaryData = getCurator().getData().forPath(path);
        return binaryData;
    }

    public List<String> getChildren(String path) throws Exception {
        return getCurator().getChildren().forPath(path);
    }
    
    
   

    public void deletePath(String path) throws Exception {
        getCurator().delete().deletingChildrenIfNeeded().forPath(path);
    }

    public void createPath(String path, byte[] data, CreateMode createMode)
            throws Exception {

        if (checkExist(path)) {
            throw new RuntimeException(path);
        }

        getCurator().create().creatingParentsIfNeeded().withMode(createMode)
                .forPath(path, data);
    }

    public boolean checkExist(String path) throws Exception {
        CuratorFramework curator = getCurator();
        return null != curator.checkExists().forPath(path);
    }

    public void awaitPathNotExist(String path) throws Exception {

        long beforeTime = System.currentTimeMillis();

        while (true) {
            if (checkExist(path)) {
                Thread.sleep(500L);
                if (System.currentTimeMillis() - beforeTime >= 10L * 1000L) {
                    logger.error("", new RuntimeException("error: zoo keeper path check exist error"));
                    beforeTime = System.currentTimeMillis();
                }
            } else {
                return;
            }

        }

    }
    
}
