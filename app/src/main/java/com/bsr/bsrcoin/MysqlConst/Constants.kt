package com.bsr.bsrcoin.MysqlConst

class Constants {
    companion object {
//        const val ROOT_URL = "http://ewallet.visanka.in/Ewallet"
//        const val ROOT_URL = "http://192.168.0.21/visankat/"
        const val ROOT_URL="https://montyapis.000webhostapp.com/visankat/"

        //Agent Specific --> Insurance Approval &  Agent's wallet
        const val url_agentApproval= ROOT_URL+"hiddenagentdetail.php"                       //subham
        const val url_insuraceApproval= ROOT_URL+"insuranceApproval.php"                    //subham
        const val url_insuracelist= ROOT_URL+"insurancelist.php"                            //subham
        const val url_AgentWallet= ROOT_URL+"AgentWallet.php"                               //subham

        //Referal
        const val url_getUserReferalCode= ROOT_URL+"getUserReferal.php"                     //subham
        const val url_updateReferral= ROOT_URL+"updateReferral.php"                         //subham
        const val url_checkRefActivation= ROOT_URL+"checkRefActivation.php"                 //subham
        const val url_ProcessReferal= ROOT_URL+"ProcessReferal.php"                         //subham
        const val url_getAllUser= ROOT_URL+"getUsers.php"                                   //subham

        //Membership
        const val url_verifyMembership= ROOT_URL+"verify_membership.php"                    //subham
        const val url_insert_NewMembership_Detail= ROOT_URL+"insert_membership_detail.php"  //subham
        const val url_membershipDetail= ROOT_URL+"membershipDetail.php"                     //subham

        //Coin
        const val url_coinhistory= ROOT_URL+"coinhistory.php"                               //subham

        //Contacts
        const val url_insertContacts= ROOT_URL+"insertContacts.php"                         //subham
        const val url_getContacts= ROOT_URL+"getContacts.php"                               //subham


     // User
        const val url_login = ROOT_URL + "user/login.php"
        const val url_register = ROOT_URL + "user/register.php"
        const val url_agent = ROOT_URL + "user/getagent.php"
        const val url_reference = ROOT_URL + "user/reference.php"
        const val url_read = ROOT_URL + "user/read.php?id="
        const val url_update_wallet_coin_val = ROOT_URL + "user/updatewalletcoinval.php"
        const val url_getid = ROOT_URL + "user/getid.php"
        const val url_get_userInfo = ROOT_URL + "user/read"
        const val url_upload_docs = ROOT_URL + "user/adddocuments.php"
        const val url_get_docs = ROOT_URL + "user/getdocuments.php"
        const val url_buy_coin = ROOT_URL + "user/buy.php"
        const val url_set_token = ROOT_URL + "user/settoken.php"

        // Cheque
        const val url_add_cheque = ROOT_URL + "user/addcheque.php"
        const val url_accept_cheque = ROOT_URL + "cheque/acceptcheque.php"
        const val url_get_cheque = ROOT_URL + "cheque/getcheque.php?userId="
        const val url_send_cheque = ROOT_URL + "cheque/sendcheque.php"

        // Loan
        const val url_loan_create = ROOT_URL + "loan/create.php"
        const val url_loan_update = ROOT_URL + "loan/update.php"
        const val url_loan_status = ROOT_URL + "loan/status.php"
        const val url_loan_view = ROOT_URL + "loan/view.php"
        const val url_loan_request = ROOT_URL + "loan/requestupdate.php"
        const val url_loan_request_inr = ROOT_URL + "loan/requestinr.php"
        const val url_loan_image = ROOT_URL + "loan/loan_images/"

        // Insurance
        const val url_insurance_create = ROOT_URL + "insurance/create.php"
        const val url_insurance_update = ROOT_URL + "insurance/update.php"
        const val url_insurance_status = ROOT_URL + "insurance/status.php"
        const val url_insurance_view = ROOT_URL + "insurance/view.php"
        const val url_insurance_image = ROOT_URL + "insurance/insurance_image/"
        const val url_insurance_request = ROOT_URL + "insurance/requestupdate.php"

        // Payment Interface
        const val url_razorpay = ROOT_URL + "razorpay/api.php?amount="

        // coin
        const val url_get_coin_price = ROOT_URL + "coin/read.php"

        //Transaction
        const val url_sendMoney = ROOT_URL + "user/sendmoney.php"
        const val url_take_permission = ROOT_URL + "user/askpermission.php "
        const val url_get_id = ROOT_URL + "user/getid.php"
        const val url_create_transaction = ROOT_URL + "transaction/create"
        const val url_get_walletId = ROOT_URL + "wallet/getid.php"

        //wallet
        const val url_add_wallet_coin = ROOT_URL + "wallet/addcoin.php"
        const val url_get_requests = ROOT_URL + "user/getaddrequest.php"
        const val url_set_status = ROOT_URL + "user/setaddstatus.php"
        const val url_get_requests_image = ROOT_URL + "user/payment_images/"

        //deposit
        const val url_create_deposit = ROOT_URL + "deposit/create"
        const val url_renew_deposit = ROOT_URL + "deposit/update"
        const val url_pay_deposit_due = ROOT_URL + "deposit/payDeposit.php"

        //firebase
        const val BASE_URL = "https://fcm.googleapis.com"
        const val SERVER_KEY = "AAAAQQ_32vQ:APA91bHguCpw-V8wmAXChY7N5gqp5oMGS7dahN9fg6Yxib3dgngedGcL8SB5CrPAzS3o_xqCU7lLwY6bHRJykKhUVZOHxRVg4gG72gbuf4iwZIqNMQNXn_kfSoWA0pCZimdNow4iRf01"
        const val CONTENT_TYPE = "application/json"

        //permissiondetails
        const val url_PermissionDetails = ROOT_URL + "permissiondetails/permissiondetails.php"
        const val url_PermissionRead= ROOT_URL + "permissiondetails/permissionread.php"

        const val MY_SOCKET_TIMEOUT_MS=100000
    }
}