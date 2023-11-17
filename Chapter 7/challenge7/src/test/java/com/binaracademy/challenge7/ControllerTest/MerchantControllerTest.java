package com.binaracademy.challenge7.ControllerTest;

import com.binaracademy.challenge7.Controller.MerchantController;
import com.binaracademy.challenge7.Model.Merchant;
import com.binaracademy.challenge7.Model.Response.MerchantResponse;
import com.binaracademy.challenge7.Service.MerchantService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MerchantControllerTest {

    @InjectMocks
    private MerchantController merchantController;

    @Mock
    private MerchantService merchantService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddMerchant() {
        Merchant merchant = new Merchant();
        Merchant createdMerchant = new Merchant();
        //mensimulasikan addMerchant dalam merchantService akan mengembalikan createdMerchant
        when(merchantService.addMerchant(any(Merchant.class))).thenReturn(createdMerchant);

        //memanggil addMerchant dalam controller
        ResponseEntity<MerchantResponse> response = merchantController.addMerchant(merchant);

        //memeriksa response status sesuai yang diharapkan
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateMerchantStatus() throws Exception {
        Long merchantCode = 123L;
        boolean open = true;
        Merchant updatedMerchant = new Merchant();
        //mensimulasikan updateMerchant dalam merchantService akan mengembalikan updateMerchant yang dibuat
        when(merchantService.updateMerchantStatus(merchantCode, open)).thenReturn(updatedMerchant);

        //memanggil updatemerchant dalam controller
        ResponseEntity<MerchantResponse> response = merchantController.updateMerchantStatus(merchantCode, open);

        //memeriksa response
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllMerchants() {
        List<Merchant> merchants = new ArrayList<>();
        when(merchantService.getAllMerchants()).thenReturn(merchants);

        ResponseEntity<List<MerchantResponse>> response = merchantController.getAllMerchants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetOpenMerchants() {
        List<Merchant> openMerchants = new ArrayList<>();
        when(merchantService.getOpenMerchants()).thenReturn(openMerchants);

        ResponseEntity<List<MerchantResponse>> response = merchantController.getOpenMerchants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
