module int_control(
    input clk,
    input INT_1,
    input INT_2,
    input [15:0] k,
    output reg int_handle,
    output reg [7:0] int_v,
    output reg int_save,
    output reg int_load 
);


parameter IDLE = 0,
          CHECK_K = 1;

reg [2:0] state, next_state;

always @(negedge clk) begin
        case (state)
            IDLE: begin
                if (INT_1) begin
                    int_v <= 8'h08;
                    int_save <= 1;
                    int_handle <= 1;
					next_state <= CHECK_K;
                end else if (INT_2) begin
                    int_v <= 8'h02;
                    int_save <= 1;
                    int_handle <= 1;
					next_state <= CHECK_K;
                end
            end
            CHECK_K: begin
                if (k[15:12]==4'hd) begin
                    next_state <= IDLE;
                    int_load <= 1;
                    int_handle <= 0;
                end
            end
            default: next_state <= IDLE;
        endcase
        
        if (next_state != state) begin
            int_save <= 0;
            int_load <= 0;
            state <= next_state;
        end
    end
endmodule