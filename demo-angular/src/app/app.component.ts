import { Component, OnInit } from '@angular/core';
import { Client, IFrame, IMessage } from '@stomp/stompjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  salesData: any[] = [];
  client!: Client; // Use definite assignment assertion
  displayedColumns: string[] = ['product', 'store', 'salesUnits', 'salesRevenue'];

  ngOnInit() {
    this.client = new Client({
      webSocketFactory: () => {
        return new WebSocket('ws://localhost:8082/ws') as any; // Type assertion
      },
      connectHeaders: {
        // Add any custom headers here if required
      },
      // beforeConnect: (frame: IFrame) => {
      //   // Perform any actions before the connection is established
      // },
      onConnect: (frame: IFrame) => {
        this.client.subscribe('/topic/salesData', (message: IMessage) => {
          this.processSalesData(message.body);
        });
      }
    });

    this.client.activate();
  }

  processSalesData(message: string) {
    let data: any[];

    try {
      data = JSON.parse(message);
    } catch (error) {
      console.error('Error parsing message:', error);
      return;
    }

    // Assuming the response is a list/array of sales data objects
    this.salesData = data.map((item) => ({ ...item }));
  }
}
