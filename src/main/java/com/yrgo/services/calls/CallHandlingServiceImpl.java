package com.yrgo.services.calls;

import com.yrgo.domain.Action;
import com.yrgo.domain.Call;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import com.yrgo.services.diary.DiaryManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class CallHandlingServiceImpl implements CallHandlingService{

    private CustomerManagementService customermanagementservice;
    private DiaryManagementService diarymanagementservice;
    public CallHandlingServiceImpl(CustomerManagementService customermanagementservice, DiaryManagementService diarymanagementservice) {
        this.customermanagementservice = customermanagementservice;
        this.diarymanagementservice = diarymanagementservice;
    }
    public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException {
        customermanagementservice.recordCall(customerId, newCall);

        for (Action action : actions) {
            diarymanagementservice.recordAction(action);
        }
    }
}
