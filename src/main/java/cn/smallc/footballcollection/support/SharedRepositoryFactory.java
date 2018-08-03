package cn.smallc.footballcollection.support;

import cn.smallc.footballcollection.repository.AccountRepository;
import cn.smallc.footballcollection.repository.page.IMGRepository;
import cn.smallc.footballcollection.repository.page.PageRepository;
import cn.smallc.footballcollection.repository.visited.VisitedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SharedRepositoryFactory {

    /**
     * 账号仓储
     */
    public static AccountRepository accountRepository;

    /**
     * 页面仓储
     */
    private static PageRepository pageRepository;

    /**
     * 历史访问仓储
     */
    private static VisitedRepository visitedRepository;

    /**
     * 图片仓储
     */
    private static IMGRepository imgRepository;

    public static IMGRepository getImgRepository() {
        return imgRepository;
    }

    @Autowired
    public void setImgRepository(IMGRepository imgRepository) {
        SharedRepositoryFactory.imgRepository = imgRepository;
    }

    public static PageRepository getPageRepository() {
        return pageRepository;
    }

    @Autowired
    public void setPageRepository(PageRepository pageRepository) {
        SharedRepositoryFactory.pageRepository = pageRepository;
    }

    public static VisitedRepository getVisitedRepository() {
        return visitedRepository;
    }

    @Autowired
    public void setVisitedRepository(VisitedRepository visitedRepository) {
        SharedRepositoryFactory.visitedRepository = visitedRepository;
    }

    public static AccountRepository getAccountRepository() {
        return accountRepository;
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        SharedRepositoryFactory.accountRepository = accountRepository;
    }
}
